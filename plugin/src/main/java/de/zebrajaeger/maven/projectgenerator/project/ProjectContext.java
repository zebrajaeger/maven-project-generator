package de.zebrajaeger.maven.projectgenerator.project;

import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.File;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ProjectContext {
    private File workingDirectory;
    private File jarFile;
    private List<Property> properties;
    private ResourceManager resources;

    public ProjectContext(File workingDirectory, File jarFile, List<Property> properties, ResourceManager resources) {
        this.workingDirectory = workingDirectory;
        this.jarFile = jarFile;
        this.properties = properties;
        this.resources = resources;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public File getJarFile() {
        return jarFile;
    }

    public List<Property> getProperties() {
        return properties;
    }
    public Property getProperty(String name) {
        for(Property p : properties){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public ResourceManager getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
