package de.zebrajaeger.maven.projectgenerator.testproject;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.IProjectGenerator;

@AutoService(IProjectGenerator.class)
public class ProjectGenerator implements IProjectGenerator {
    @Override
    public void generate() {
        System.out.println("GENERATE!!!!!!!");
    }
}
