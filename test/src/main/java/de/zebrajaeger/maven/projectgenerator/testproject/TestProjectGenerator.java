package de.zebrajaeger.maven.projectgenerator.testproject;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.ProjectContext;
import de.zebrajaeger.maven.projectgenerator.ProjectGenerator;
import de.zebrajaeger.maven.projectgenerator.RequiredProperty;
import de.zebrajaeger.maven.projectgenerator.utils.RandomUUID;
import de.zebrajaeger.maven.projectgenerator.utils.ResourceUtils;
import de.zebrajaeger.maven.projectgenerator.templateengine.DefaultTemplateProcessor;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngineException;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateProcessor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@AutoService(de.zebrajaeger.maven.projectgenerator.ProjectGenerator.class)
@SuppressWarnings("unused")
public class TestProjectGenerator implements ProjectGenerator {

    @Override
    public void generate(ProjectContext context) throws MojoExecutionException, MojoFailureException {
        ResourceUtils resourceUtils = new ResourceUtils(context.getResources());
        TemplateProcessor templateProcessor = DefaultTemplateProcessor.of();
        templateProcessor.getTemplateContext().put("randomUUID", new RandomUUID());
        try {
            resourceUtils.copy(ResourceUtils.PROJECT_TEMPLATE, context.getWorkingDirectory(), templateProcessor, true);
        } catch (IOException | TemplateEngineException e) {
            throw new MojoExecutionException("Can not copy resources to '" + context.getWorkingDirectory().getAbsolutePath() + "'", e);
        }
    }

    @Override
    public List<RequiredProperty> getRequiredProperties() {
        return Collections.emptyList();
    }
}
