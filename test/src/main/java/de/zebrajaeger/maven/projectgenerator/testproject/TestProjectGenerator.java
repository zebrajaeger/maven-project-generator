package de.zebrajaeger.maven.projectgenerator.testproject;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.ProjectContext;
import de.zebrajaeger.maven.projectgenerator.ProjectGenerator;
import de.zebrajaeger.maven.projectgenerator.RequiredProperty;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;
import de.zebrajaeger.maven.projectgenerator.templateengine.DefaultTemplateProcessor;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngineException;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateProcessor;
import de.zebrajaeger.maven.projectgenerator.utils.CopyTask;
import de.zebrajaeger.maven.projectgenerator.utils.RandomUUID;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@AutoService(de.zebrajaeger.maven.projectgenerator.ProjectGenerator.class)
@SuppressWarnings("unused")
public class TestProjectGenerator implements ProjectGenerator {

    public static final String PROJECT_TEMPLATE = "project_template";

    @Override
    public void generate(ProjectContext context) throws MojoExecutionException, MojoFailureException {
        TemplateProcessor templateProcessor = DefaultTemplateProcessor.of();
        templateProcessor.getTemplateContext().put("randomUUID", new RandomUUID());
        try {
            CopyTask.of(
                    context.getResources(),
                    ResourcePath.of(PROJECT_TEMPLATE),
                    context.getWorkingDirectory())
                    .copy();

        } catch (IOException | TemplateEngineException e) {
            throw new MojoExecutionException("Can not copy resources to '" + context.getWorkingDirectory().getAbsolutePath() + "'", e);
        }
    }

    @Override
    public List<RequiredProperty> getRequiredProperties() {
        return Collections.emptyList();
    }
}