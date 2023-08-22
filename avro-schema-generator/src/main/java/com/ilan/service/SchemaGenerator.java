package com.ilan.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import com.ilan.constant.SchemaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
public class SchemaGenerator {

    //        ObjectMapper mapper = new ObjectMapper(new AvroFactory());
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        AvroSchemaGenerator gen = new AvroSchemaGenerator();
//        mapper.acceptJsonFormatVisitor(Employee.class, gen);

    AvroMapper defaultAvroMapper = AvroMapper.builder()
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .enable(MapperFeature.APPLY_DEFAULT_VALUES)
            //.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .addModule(new AvroJavaTimeModule())
            .build();

    public void createAvroSchemaFromClass(Class<?> clazz, AvroMapper avroMapper, String extension, String nameSpacePrefix, String outputDirectory) throws IOException {
        AvroSchemaGenerator gen = new AvroSchemaGenerator();
        gen.enableLogicalTypes();
        if (Objects.nonNull(avroMapper)) {
            avroMapper.acceptJsonFormatVisitor(clazz, gen);
        } else {
            this.defaultAvroMapper.acceptJsonFormatVisitor(clazz, gen);
        }
        AvroSchema schemaWrapper = gen.getGeneratedSchema();
        org.apache.avro.Schema avroSchema = schemaWrapper.getAvroSchema();
        String avroSchemaInJSON = avroSchema.toString(Boolean.TRUE);

        String actualNameSpace = avroSchema.getNamespace();
        log.info("user has the config package prefix as :: {}" + nameSpacePrefix);
        String overrideNameSpace = nameSpacePrefix + SchemaConstant.PACKAGE_SPLITTER + avroSchema.getNamespace();

        org.apache.avro.Schema schemaInAvroNameSpace = new Schema
                .Parser()
                .parse(avroSchemaInJSON.replace(actualNameSpace, overrideNameSpace));

        log.info("outputDirectory {}", outputDirectory);
        //Write to File
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputDirectory + clazz.getSimpleName() + SchemaConstant.EXTENSION_SPLITTER + extension));
        writer.write(schemaInAvroNameSpace.toString(Boolean.TRUE));
        writer.close();

    }
}
