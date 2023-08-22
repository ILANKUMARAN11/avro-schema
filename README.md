```xml
<build>
    <plugins>

        <!-- Hi Ilan this is your plugin STARTS-->
        <plugin>
            <groupId>com.ilan.schema</groupId>
            <artifactId>avro-schema-plugin</artifactId>
            <version>${project.version}</version>
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
                            <exclude>**/*.avro</exclude>
                            <exclude>**/*.avsc</exclude>
                        </excludes>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        
        <!-- Hi Ilan this is your plugin END-->
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