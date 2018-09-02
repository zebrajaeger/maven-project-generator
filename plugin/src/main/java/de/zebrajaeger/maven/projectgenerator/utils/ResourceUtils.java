package de.zebrajaeger.maven.projectgenerator.utils;

import de.zebrajaeger.maven.projectgenerator.resources.AcceptAllResourceFilter;
import de.zebrajaeger.maven.projectgenerator.resources.Item;
import de.zebrajaeger.maven.projectgenerator.resources.Resource;
import de.zebrajaeger.maven.projectgenerator.resources.ResourceFilter;
import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import de.zebrajaeger.maven.projectgenerator.resources.ResourcePath;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngineException;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateProcessor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ResourceUtils {

    public static final ResourcePath PROJECT_TEMPLATE = ResourcePath.of("project_template");
    public static final ResourcePath META_INF = ResourcePath.of("META_INF");

    private ResourceManager resourceManager;

    public ResourceUtils(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void copy(ResourcePath path, File targetDirectory, boolean recursive) throws IOException, TemplateEngineException {
        copy(path, null, null, targetDirectory, recursive);
    }

    public void copy(ResourcePath path, File targetDirectory, TemplateProcessor templateProcessor, boolean recursive) throws IOException, TemplateEngineException {
        copy(path, null, templateProcessor, targetDirectory, recursive);
    }

    public void copy(ResourcePath path, File targetDirectory, ResourceFilter resourceFilter, boolean recursive) throws IOException, TemplateEngineException {
        copy(path, resourceFilter, null, targetDirectory, recursive);
    }

    public void copy(ResourcePath path, ResourceFilter filter, TemplateProcessor templateProcessor, File targetDirectory, boolean recursive) throws IOException, TemplateEngineException {
        if (filter == null) {
            filter = new AcceptAllResourceFilter();
        }

        List<Item> items = resourceManager.getItems(path, recursive)
                .stream()
                .filter(filter)
                .collect(Collectors.toList());

        // for exception handling: oldschool iterations
        for (Item i : items) {
            ResourcePath resourcePath = i.getPath().removeParent(path);
            if (resourcePath.isEmpty()) {
                continue;
            }

            File f = new File(targetDirectory, resourcePath.toString());
            if (i.isNode()) {
                f.mkdirs();
            } else if (i.isResource()) {
                Resource resource = (Resource) i;
                if (templateProcessor != null) {
                    String content = new String(resource.getContent(), StandardCharsets.UTF_8);
                    content = templateProcessor.convert(content);
                    FileUtils.write(f, content, StandardCharsets.UTF_8);
                } else {
                    FileUtils.writeByteArrayToFile(f, resource.getContent());
                }
            }
        }
    }
}
