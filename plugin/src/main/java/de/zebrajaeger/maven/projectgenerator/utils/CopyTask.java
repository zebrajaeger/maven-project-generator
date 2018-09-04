package de.zebrajaeger.maven.projectgenerator.utils;

import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import de.zebrajaeger.maven.projectgenerator.resources.model.Item;
import de.zebrajaeger.maven.projectgenerator.resources.model.Resource;
import de.zebrajaeger.maven.projectgenerator.resources.model.ResourceFilter;
import de.zebrajaeger.maven.projectgenerator.resources.path.RecourcePathTransformer;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngineException;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateProcessor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class CopyTask {
    private ResourceManager resourceManager;
    private ResourcePath from;
    private File to;
    private boolean recursive = false;
    private List<RecourcePathTransformer> resourcePathTransformers = new LinkedList<>();
    private List<ResourceFilter> filters = new LinkedList<>();
    private TemplateProcessor templateProcessor;

    public static CopyTask of(ResourceManager resourceManager, ResourcePath from, File to) {
        return new CopyTask(resourceManager, from, to);
    }

    private CopyTask(ResourceManager resourceManager, ResourcePath from, File to) {
        this.resourceManager = resourceManager;
        this.from = from;
        this.to = to;
    }

    public CopyTask recourcePathTransformer(RecourcePathTransformer transformer) {
        resourcePathTransformers.add(transformer);
        return this;
    }

    public CopyTask resourceFilter(ResourceFilter filter) {
        filters.add(filter);
        return this;
    }

    public CopyTask templateProcessor(TemplateProcessor processor) {
        templateProcessor = processor;
        return this;
    }

    public void copy() throws TemplateEngineException, IOException {
        List<Item> items = resourceManager.getItems(from, recursive);
        for (Item i : items) {
            ResourcePath resourcePath = i.getPath().removeParent(from);
            if (resourcePath.isEmpty() || !acceptItem(i)) {
                continue;
            }

            // copy file
            File f = new File(to, transformPath(resourcePath).toString());
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

    private ResourcePath transformPath(ResourcePath resourcePath) {
        ResourcePath targetPath = resourcePath;
        for(RecourcePathTransformer t : resourcePathTransformers){
            targetPath = t.apply(targetPath);
        }
        return targetPath;
    }

    private boolean acceptItem(Item i) {
        boolean accept = true;
        for(ResourceFilter f : filters){
            Boolean a = f.accept(i);
            if(a==Boolean.TRUE){
                accept = true;
                break;
            }
            if(a==Boolean.FALSE){
                accept = false;
                break;
            }
        }
        return accept;
    }
}
