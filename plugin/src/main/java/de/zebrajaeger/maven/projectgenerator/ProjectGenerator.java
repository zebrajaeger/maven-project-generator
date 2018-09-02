package de.zebrajaeger.maven.projectgenerator;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.util.List;

public interface ProjectGenerator {
    void generate(ProjectContext context) throws MojoExecutionException, MojoFailureException;

    List<RequiredProperty> getRequiredProperties();
}
