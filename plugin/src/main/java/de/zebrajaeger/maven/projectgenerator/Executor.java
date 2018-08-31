package de.zebrajaeger.maven.projectgenerator;

import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import de.zebrajaeger.maven.projectgenerator.query.PrompterException;
import de.zebrajaeger.maven.projectgenerator.query.Queryer;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Executor {
    @SuppressWarnings("unused")
    public void exec(String jarPath) throws MojoExecutionException {
        System.out.println("PATH: " + jarPath);

        ResourceManager rm = new ResourceManager();
        File jarFile = new File(jarPath);
        try {
            rm.addSource(jarFile);
        } catch (IOException e) {
            throw new MojoExecutionException("failed to loade template resources from '" + jarPath + "'", e);
        }

        ServiceLoader<ProjectGenerator> services = ServiceLoader.load(ProjectGenerator.class, this.getClass().getClassLoader());
        for (ProjectGenerator g : services) {
            List<Property> properties = getProperties(g.getRequiredProperties());
            try {
                populateProperties(properties);
            } catch (PrompterException e) {
                throw new MojoExecutionException("Prompter failed", e);
            }
            g.generate(new ProjectContext(jarFile, properties, rm));
        }
    }

    private void populateProperties(List<Property> properties) throws PrompterException {
        Queryer q = new Queryer();
        for (Property p : properties) {
            if (StringUtils.isBlank(p.getValue())) {
                String value = q.getPropertyValue(p.getName(), p.getDefaultValue(), p.getValidation());
                p.setValue(value);
            }
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
