package de.zebrajaeger.maven.projectgenerator.testproject;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.IProjectGenerator;
import de.zebrajaeger.maven.projectgenerator.jar.ResourceManager;

@AutoService(IProjectGenerator.class)
public class ProjectGenerator implements IProjectGenerator {
    @Override
    public void generate(ResourceManager rm) {
        System.out.println("GENERATE!!!!!!!");
    }
}
