package de.zebrajaeger.maven.projectgenerator;

import de.zebrajaeger.maven.projectgenerator.jar.ResourceManager;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.zip.ZipFile;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Executor {
    @SuppressWarnings("unused")
    public void exec(String jarPath) throws MojoExecutionException {
        System.out.println("PATH: " + jarPath);

        ResourceManager rm = new ResourceManager();
        try {
            rm.addSource(new File(jarPath));
        } catch (IOException e) {
            throw new MojoExecutionException("failed to loade template resources from '" + jarPath + "'", e);
        }

        ServiceLoader<IProjectGenerator> services = ServiceLoader.load(IProjectGenerator.class, this.getClass().getClassLoader());
        Iterator<IProjectGenerator> iterator = services.iterator();
        while (iterator.hasNext()) {
            IProjectGenerator g = iterator.next();
            g.generate(rm);
        }
    }
}
