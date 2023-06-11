package com.ilan.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ilan.constant.SchemaConstant.PACKAGE_SPLITTER;

@Slf4j
public class FileUtils {

    public static void getAllFiles(File curDir, Map<String, List<String>> directoryMapping, String nameSpacePrefix) {
        File[] filesList = curDir.listFiles();
        for (File f : filesList) {
            if (f.isDirectory()) {
                getAllFiles(f, directoryMapping, nameSpacePrefix);
            }
            if (f.isFile()) {
                if (f.getName().endsWith(".class") && !f.getName().endsWith("$Builder.class")) {
                    String packageName = getPackageName(f);
                    String className = getClassName(f);

                    if (!packageName.startsWith(nameSpacePrefix) || packageName.startsWith(nameSpacePrefix)) {
                        if (directoryMapping.containsKey(packageName)) {
                            List<String> existingPath = directoryMapping.get(packageName);
                            existingPath.add(className);
                        } else {
                            List<String> newPath = new ArrayList<>();
                            newPath.add(className);
                            directoryMapping.put(packageName, newPath);
                        }
                    }
                }
            }
        }
    }

    public static String getPackageName(File f) {
        String folderParent = f.getParent().replace("\\", PACKAGE_SPLITTER);
        int indexOfTarget = folderParent.indexOf("target.classes.") + 15;
        String packageName = folderParent.substring(indexOfTarget);
        return packageName;
    }

    public static String getClassName(File f) {
        String absolutePath = f.getAbsolutePath().replace("\\", PACKAGE_SPLITTER);
        log.debug(f.getName() + " :: " + absolutePath);
        int indexOfTarget = absolutePath.indexOf("target.classes.") + 15;
        String packageWithClass = absolutePath.substring(indexOfTarget);
        int indexOfClass = packageWithClass.indexOf(".class");
        String className = packageWithClass.substring(0, indexOfClass);
        return className;
    }
}
