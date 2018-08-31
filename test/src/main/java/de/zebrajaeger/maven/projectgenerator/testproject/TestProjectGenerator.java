package de.zebrajaeger.maven.projectgenerator.testproject;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.ProjectContext;
import de.zebrajaeger.maven.projectgenerator.ProjectGenerator;
import de.zebrajaeger.maven.projectgenerator.RequiredProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@AutoService(de.zebrajaeger.maven.projectgenerator.ProjectGenerator.class)
@SuppressWarnings("unused")
public class TestProjectGenerator implements ProjectGenerator {

    @Override
    public void generate(ProjectContext context) {
        System.out.println("GENERATE!!!!!!!" + context);
    }

    @Override
    public List<RequiredProperty> getRequiredProperties() {
        List<RequiredProperty> requiredProperties = new LinkedList<>();
        requiredProperties.add(new RequiredProperty("foo"));
        requiredProperties.add(new RequiredProperty("xx1", "yepp", null));
        requiredProperties.add(new RequiredProperty("yy1", Pattern.compile("\\d+")));
        return requiredProperties;
    }
}
