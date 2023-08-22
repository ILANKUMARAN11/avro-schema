```xml
<build>
    <plugins>

        <!-- 
           1) The plugins are required to be in order as shown here. 
           2) Phase of the both the plugin should be in compile phase.
        -->

        <!-- This converts the java file to avsc file and puts it in resource folder-->
        <plugin>
            <groupId>com.ilan.schema</groupId>
            <artifactId>avro-schema-plugin</artifactId>
            <version>0.0.1</version>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>avro-schema</goal>
                    </goals>
                    <configuration>
                        <sourceDirectory>${project.basedir}/target/classes/,</sourceDirectory>
                        <outputDirectory>${project.basedir}/src/main/resources/</outputDirectory>
                        <extension>avsc</extension>
                        <nameSpacePrefix>avro</nameSpacePrefix>
                        <excludes>
                            <exclude>**/*.java</exclude>
                            <exclude>**/*.java</exclude>
                        </excludes>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        
        <!-- This converts avsc file to avro java serialized file-->
        <plugin>
            <groupId>org.apache.avro</groupId>
            <artifactId>avro-maven-plugin</artifactId>
            <version>1.10.0</version>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>schema</goal>
                    </goals>
                    <configuration>
                        <sourceDirectory>${project.basedir}/src/main/resources/</sourceDirectory>
                        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                        <includes>
                            <include>**/*.avro</include>
                            <include>**/*.avsc</include>
                        </includes>
                    </configuration>
                </execution>
            </executions>
        </plugin>

    </plugins>
    
</build>
```