import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Earth;
import pojo.Employee;
import pojo.Mars;
import pojo.Universe;
import com.ilan.service.SchemaGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.ilan.constant.SchemaConstant.*;


@Slf4j
public class SchemaGeneratorTest {

    static AvroMapper avroMapper = null;
    static String testResource = null;

    @BeforeAll
    static void setup() {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        testResource = absolutePath + "/";

        File folder = new File(testResource);
        Arrays.stream(folder.listFiles())
                .filter(f -> f.getName().endsWith(EXTENSION_SPLITTER + AVRO_EXTENSION) || f.getName().endsWith(EXTENSION_SPLITTER + AVRO_EXTENSION))
                .peek(f -> log.info(f.getName() + " is deleted"))
                .forEach(File::delete);

        avroMapper = AvroMapper.builder()
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .enable(MapperFeature.APPLY_DEFAULT_VALUES)
                //.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .addModule(new AvroJavaTimeModule())
                .build();

    }

    @Test
    @DisplayName("avro extension generate ")
    public void generateAvro() throws IOException, ClassNotFoundException {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        schemaGenerator.createAvroSchemaFromClass(Employee.class, avroMapper, AVRO_EXTENSION, "avro", null, testResource);
        Assertions.assertTrue(getFileNameFromClass(Employee.class, AVRO_EXTENSION));
    }

    @Test
    @DisplayName("avsc extension generate ")
    public void generateAvsc() throws IOException {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        schemaGenerator.createAvroSchemaFromClass(Universe.class, avroMapper, AVSC_EXTENSION, "avro", null, testResource);
        schemaGenerator.createAvroSchemaFromClass(Earth.class, avroMapper, AVSC_EXTENSION, "avro", null, testResource);
        schemaGenerator.createAvroSchemaFromClass(Mars.class, avroMapper, AVSC_EXTENSION, "avro", null, testResource);

        Assertions.assertTrue(getFileNameFromClass(Universe.class, AVSC_EXTENSION));
        Assertions.assertTrue(getFileNameFromClass(Earth.class, AVSC_EXTENSION));
        Assertions.assertTrue(getFileNameFromClass(Mars.class, AVSC_EXTENSION));
    }

    Boolean getFileNameFromClass(Class<?> clazz, String extension) {
        String[] className = clazz.getName().split("[.]");
        String lastOne = className[className.length - 1];
        File tempFile = new File(testResource + lastOne + EXTENSION_SPLITTER + extension);
        log.info("File name of the avro generated ::: {}", tempFile.getAbsolutePath());
        boolean exists = tempFile.exists();
        return exists;
    }
}
