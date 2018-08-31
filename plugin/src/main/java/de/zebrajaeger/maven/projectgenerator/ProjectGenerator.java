package de.zebrajaeger.maven.projectgenerator;

import java.util.List;

public interface ProjectGenerator {
    void generate(ProjectContext context);

    List<RequiredProperty> getRequiredProperties();
}
