package de.zebrajaeger.maven.projectgenerator.opencms;

import com.google.auto.service.AutoService;
import de.zebrajaeger.maven.projectgenerator.project.ProjectContext;
import de.zebrajaeger.maven.projectgenerator.project.ProjectGenerator;
import de.zebrajaeger.maven.projectgenerator.project.RequiredProperty;
import de.zebrajaeger.maven.projectgenerator.resources.model.FileExtensionResourceFilter;
import de.zebrajaeger.maven.projectgenerator.resources.model.FileNameResourceFilter;
import de.zebrajaeger.maven.projectgenerator.resources.model.FilterChain;
import de.zebrajaeger.maven.projectgenerator.resources.path.PackagePathTrandformer;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;
import de.zebrajaeger.maven.projectgenerator.resources.path.StringReplacerPathTransformer;
import de.zebrajaeger.maven.projectgenerator.templateengine.DefaultTemplateProcessor;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateContext;
import de.zebrajaeger.maven.projectgenerator.utils.Coordinate;
import de.zebrajaeger.maven.projectgenerator.utils.CopyTask;
import de.zebrajaeger.maven.projectgenerator.utils.RandomUUID;
import de.zebrajaeger.maven.projectgenerator.utils.StringList;
import de.zebrajaeger.maven.projectgenerator.utils.stringreplacer.DefaultReplacementDictionary;
import de.zebrajaeger.maven.projectgenerator.utils.stringreplacer.StringReplacer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@AutoService(ProjectGenerator.class)
@SuppressWarnings("unused")
public class OpenCmsProjectGenerator implements ProjectGenerator {

    public static final String PROJECT_TEMPLATE = "project_template";
    public static final String PROJECT_COORDINATE = "projectCoordinate";
    public static final String MODULES = "modules";

    @Override
    public void generate(ProjectContext context) throws MojoExecutionException, MojoFailureException {

        String projectCoordinate = context.getProperty(PROJECT_COORDINATE).getValue();
        Coordinate coordinate = Coordinate.of(projectCoordinate);
        StringList modules = StringList.of(context.getProperty(MODULES).getValue());

        TemplateContext templateContext = TemplateContext.of();

        // utils
        templateContext.put("randomUUID", new RandomUUID());
        templateContext.put("UUID", new RandomUUID());

        // common stuff
        templateContext.put("coordinate", coordinate);
        templateContext.put("groupId", coordinate.getGroupId());
        templateContext.put("version", coordinate.getVersion());

        templateContext.put("modules", modules);

        try {
            // root
            templateContext.put("artifactId", coordinate.getArtifactId() + ".parent");
            templateContext.put("rootArtifactId", coordinate.getArtifactId() + ".parent");
            CopyTask.of(
                    context.getResources(),
                    ResourcePath.of(PROJECT_TEMPLATE, "root"),
                    context.getWorkingDirectory())
                    .templateProcessor(DefaultTemplateProcessor.of(templateContext))
                    .recursive()
                    .copy();

            // modules
            for (String moduleName : modules) {
                templateContext.put("module", moduleName);
                templateContext.put("artifactId", coordinate.getArtifactId() + "." + moduleName);
                templateContext.put("package", coordinate.getArtifactId() + "." + moduleName);

                File moduleRoot = new File(context.getWorkingDirectory(), moduleName);
                moduleRoot.mkdirs();

                CopyTask.of(context.getResources(), ResourcePath.of(PROJECT_TEMPLATE, "module"), moduleRoot)
                        .recursive()
                        .pathTransformer(
                                PackagePathTrandformer.of(
                                        coordinate.getArtifactId()
                                )
                        )
                        .pathTransformer(
                                StringReplacerPathTransformer.of(
                                        StringReplacer.of(
                                                DefaultReplacementDictionary.of(
                                                        templateContext.getContext()
                                                )
                                        )
                                )
                        )
                        .templateProcessor(
                                FilterChain.of(
                                        false,
                                        FileNameResourceFilter.of(true, "gulpfile.js"),
                                        FileExtensionResourceFilter.of(true, "java", "xml")
                                ),
                                DefaultTemplateProcessor.of(
                                        templateContext
                                )
                        )
                        .copy();
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Can not copy resources to '" + context.getWorkingDirectory().getAbsolutePath() + "'", e);
        }
    }

    @Override
    public List<RequiredProperty> getRequiredProperties() {
        List<RequiredProperty> result = new LinkedList<>();

        result.add(new RequiredProperty("projectCoordinate", "com.test:com.testproject:0.0.1-SNAPSHOT"));
        result.add(new RequiredProperty("modules", "base"));

        return result;
    }
}
