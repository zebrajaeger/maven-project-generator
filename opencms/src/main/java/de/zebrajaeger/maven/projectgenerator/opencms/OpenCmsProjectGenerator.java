package de.zebrajaeger.maven.projectgenerator.opencms;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.ProjectContext;
import de.zebrajaeger.maven.projectgenerator.ProjectGenerator;
import de.zebrajaeger.maven.projectgenerator.RequiredProperty;
import de.zebrajaeger.maven.projectgenerator.templateengine.DefaultTemplateProcessor;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateContext;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngineException;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateProcessor;
import de.zebrajaeger.maven.projectgenerator.utils.Coordinate;
import de.zebrajaeger.maven.projectgenerator.utils.RandomUUID;
import de.zebrajaeger.maven.projectgenerator.utils.ResourceUtils;
import de.zebrajaeger.maven.projectgenerator.utils.StringList;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@AutoService(de.zebrajaeger.maven.projectgenerator.ProjectGenerator.class)
@SuppressWarnings("unused")
public class OpenCmsProjectGenerator implements ProjectGenerator {

    public static final String PROJECT_COORDINATE = "projectCoordinate";
    public static final String MODULES = "modules";

    @Override
    public void generate(ProjectContext context) throws MojoExecutionException, MojoFailureException {
        String projectCoordinate = context.getProperty(PROJECT_COORDINATE).getValue();
        Coordinate coordinate = Coordinate.of(projectCoordinate);
        StringList modules = StringList.of(context.getProperty(MODULES).getValue());

        TemplateProcessor templateProcessor = DefaultTemplateProcessor.of();
        TemplateContext templateContext = templateProcessor.getTemplateContext();
        templateContext.put("randomUUID", new RandomUUID());
        templateContext.put("coordinate", coordinate);
        templateContext.put("groupId", coordinate.getGroupId());
        templateContext.put("artifactId", coordinate.getArtifactId());
        templateContext.put("version", coordinate.getVersion());
        templateContext.put("rootArtifactId", "???");

        templateContext.put("modules", modules);

        ResourceUtils resourceUtils = new ResourceUtils(context.getResources());

        // TODO root stuff
        try {
            resourceUtils.copy(ResourceUtils.PROJECT_TEMPLATE, context.getWorkingDirectory(), templateProcessor, true);
        } catch (IOException | TemplateEngineException e) {
            throw new MojoExecutionException("Can not copy resources to '" + context.getWorkingDirectory().getAbsolutePath() + "'", e);
        }

        for(String moduleName : modules){
            templateContext.put("module", moduleName);
            templateContext.put("package", coordinate.getArtifactId() + "." + moduleName);

            // TODO module stuff
        }
    }

    @Override
    public List<RequiredProperty> getRequiredProperties() {
        List<RequiredProperty> result = new LinkedList<>();

        result.add(new RequiredProperty("projectCoordinate", "com.test:testproject:0.0.1-SNAPSHOT"));
        result.add(new RequiredProperty("modules", "basis"));

        return result;
    }
}
