package de.zebrajaeger.maven.projectgenerator;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Executor {
    @SuppressWarnings("unused")
    public void exec(String jarPath) {
        System.out.println("PATH: " + jarPath);
        ServiceLoader<IProjectGenerator> services = ServiceLoader.load(IProjectGenerator.class, this.getClass().getClassLoader());
        Iterator<IProjectGenerator> iterator = services.iterator();
        while (iterator.hasNext()) {
            IProjectGenerator g = iterator.next();
            g.generate();
        }
    }
}
