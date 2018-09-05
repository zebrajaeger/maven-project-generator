package de.zebrajaeger.maven.projectgenerator.project;

import de.zebrajaeger.maven.projectgenerator.query.PrompterException;
import de.zebrajaeger.maven.projectgenerator.query.Queryer;
import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import de.zebrajaeger.maven.projectgenerator.utils.LoggingUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Executor {
    private static LoggingUtils LOG = LoggingUtils.of(Executor.class);

    @SuppressWarnings("unused")
    public void exec(String jarPath, boolean interactiveMode) throws MojoExecutionException, MojoFailureException {
        ResourceManager rm = new ResourceManager();
        File jarFile = new File(jarPath);
        try {
            rm.addSource(jarFile);
        } catch (IOException e) {
            throw new MojoExecutionException("failed to loade template resources from '" + jarPath + "'", e);
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        ServiceLoader<ProjectGenerator> services = ServiceLoader.load(ProjectGenerator.class, this.getClass().getClassLoader());
        for (ProjectGenerator g : services) {
            List<Property> properties = getProperties(g.getRequiredProperties());
            populateProperties(properties, interactiveMode);
            g.generate(new ProjectContext(workingDirectory, jarFile, properties, rm));
        }
    }

    private void populateProperties(List<Property> properties, boolean interactiveMode) throws MojoExecutionException {
        boolean allPopulated = true;
        Queryer q = new Queryer();
        for (Property p : properties) {
            if (StringUtils.isBlank(p.getValue())) {
                String defaultValue = p.getDefaultValue();
                if (interactiveMode) {
                    // read property from command line
                    try {
                        String value = q.getPropertyValue(p.getName(), defaultValue, p.getValidation());
                        p.setValue(value);
                    } catch (PrompterException e) {
                        throw new MojoExecutionException("Prompter failed", e);
                    }
                } else {
                    if (StringUtils.isNotBlank(defaultValue)) {
                        p.setValue(defaultValue);
                    } else {
                        LOG.error("could not populate property: '{}'", p.getName());
                        allPopulated = false;
                    }
                }
            }
        }

        if (!allPopulated) {
            throw new MojoExecutionException("Not all properties could be are populated.");
        }
    }

    private List<Property> getProperties(List<RequiredProperty> requiredProperties) {
        return requiredProperties
                .stream()
                .map(p -> {
                    String value = System.getProperty(p.getName());
                    return new Property(p, value);
                })
                .collect(Collectors.toList());
    }
}
