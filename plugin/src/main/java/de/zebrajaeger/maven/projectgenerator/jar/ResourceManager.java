package de.zebrajaeger.maven.projectgenerator.jar;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ResourceManager {
    public ResourceManager(File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();

            name = name.replace('\\', '/');
            if(name.startsWith("/")){
                name = name.substring(1);
            }

            
        }
    }
}
