package de.zebrajaeger.maven.projectgenerator;

import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.File;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ProjectContext {
    private File jarFile;
    private List<Property> properties;
    private ResourceManager recources;

    public ProjectContext(File jarFile, List<Property> properties, ResourceManager recources) {
        this.jarFile = jarFile;
        this.properties = properties;
        this.recources = recources;
    }

    public File getJarFile() {
        return jarFile;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public ResourceManager getRecources() {
        return recources;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
