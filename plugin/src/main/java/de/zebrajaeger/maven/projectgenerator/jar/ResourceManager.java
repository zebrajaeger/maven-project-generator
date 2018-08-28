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
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();

            name = name.replace('\\', '/');
            if (name.startsWith("/")) {
                name = name.substring(1);
            }
        }
    }

    private void processJarResources(File file) throws IOException {
//        ZipFile zipFile = new ZipFile(file);
//        Enumeration<? extends ZipEntry> entries = zipFile.entries();
//        List<ProjectResource> projectResources = new LinkedList<>();
//        while (entries.hasMoreElements()) {
//            ZipEntry e = entries.nextElement();
//            String name = e.getName();
//            if (name.startsWith("project_template/") || name.startsWith("project_template\\")) {
//                name = name.substring(17);
//                name = name.replace('\\', '/');
//                byte[] content = IOUtils.toByteArray(zipFile.getInputStream(e));
//                ProjectResource projectResource = new ProjectResource(name, content);
//                projectResources.add(projectResource);
//            }
//            System.out.println("** " + name);
//        }
    }
}
