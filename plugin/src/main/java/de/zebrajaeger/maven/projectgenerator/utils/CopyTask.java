package de.zebrajaeger.maven.projectgenerator.utils;

import com.github.jknack.handlebars.HandlebarsException;
import de.zebrajaeger.maven.projectgenerator.resources.ResourceManager;
import de.zebrajaeger.maven.projectgenerator.resources.model.FilterChain;
import de.zebrajaeger.maven.projectgenerator.resources.model.Item;
import de.zebrajaeger.maven.projectgenerator.resources.model.Resource;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePathTransformer;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngineException;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateProcessor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class CopyTask {
    private ResourceManager resourceManager;
    private ResourcePath from;
    private File to;
    private boolean recursive = false;
    private List<ResourcePathTransformer> resourcePathTransformers = new LinkedList<>();
    private FilterChain filter = FilterChain.of(true);
    private List<ResourceProcessor> resourceProcessors = new LinkedList<>();

    public static CopyTask of(ResourceManager resourceManager, ResourcePath from, File to) {
        return new CopyTask(resourceManager, from, to);
    }

    private CopyTask(ResourceManager resourceManager, ResourcePath from, File to) {
        this.resourceManager = resourceManager;
        this.from = from;
        this.to = to;
    }

    public CopyTask pathTransformer(ResourcePathTransformer transformer) {
        resourcePathTransformers.add(transformer);
        return this;
    }

    public CopyTask filter(FilterChain value) {
        filter = value;
        return this;
    }

    public CopyTask templateProcessor(TemplateProcessor processor) {
        resourceProcessors.add(ResourceProcessor.of(processor));
        return this;
    }

    public CopyTask templateProcessor(FilterChain filter, TemplateProcessor processor) {
        resourceProcessors.add(ResourceProcessor.of(filter, processor));
        return this;
    }

    public CopyTask recursive(boolean isRecursive) {
        recursive = isRecursive;
        return this;
    }

    public CopyTask recursive() {
        return recursive(true);
    }

    public void copy() throws IOException {
        List<Item> items = resourceManager.getItems(from, recursive);
        for (Item i : items) {
            ResourcePath resourcePath = i.getPath().removeParent(from);
            if (resourcePath.isEmpty() || !filter.acceptItem(i)) {
                continue;
            }

            // copy file
            File f = new File(to, transformPath(resourcePath).toString());
            if (i.isNode()) {
                f.mkdirs();
            } else if (i.isResource()) {
                Resource resource = (Resource) i;
                String content = new String(resource.getContent(), StandardCharsets.UTF_8);
                try {
                    Optional<String> processedContent = processByTemplateProcessors(resource, content);
                    if (processedContent.isPresent()) {
                        FileUtils.write(f, processedContent.get(), StandardCharsets.UTF_8);
                    } else {
                        FileUtils.writeByteArrayToFile(f, resource.getContent());
                    }
                } catch (TemplateEngineException | HandlebarsException e) {
                    String msg = String.format("TemplateProcessor failed to process template: '%s'",
                            i.getPath());
                    throw new IOException(msg, e);
                }
            }
        }
    }

    private Optional<String> processByTemplateProcessors(Item item, String content) throws TemplateEngineException {
        boolean processed = false;
        for (ResourceProcessor rp : resourceProcessors) {
            content = rp.process(item, content);
            processed = true;
        }
        return processed ? Optional.of(content) : Optional.empty();
    }

    private ResourcePath transformPath(ResourcePath resourcePath) {
        ResourcePath targetPath = resourcePath;
        for (ResourcePathTransformer t : resourcePathTransformers) {
            targetPath = t.apply(targetPath);
        }
        return targetPath;
    }

    static class ResourceProcessor {
        private FilterChain filters;
        private TemplateProcessor templateProcessor;

        public static ResourceProcessor of(TemplateProcessor templateProcessor) {
            return new ResourceProcessor(FilterChain.of(true), templateProcessor);
        }

        public static ResourceProcessor of(FilterChain filters, TemplateProcessor templateProcessor) {
            return new ResourceProcessor(filters, templateProcessor);
        }

        private ResourceProcessor(FilterChain filters, TemplateProcessor templateProcessor) {
            this.filters = filters;
            this.templateProcessor = templateProcessor;
        }

        public String process(Item item, String content) throws TemplateEngineException {
            if (filters.acceptItem(item)) {
                templateProcessor.getTemplateContext().setSourcePath(item.getPath().toString());
                return templateProcessor.convert(content);
            }
            return content;
        }

        public FilterChain getFilters() {
            return filters;
        }

        public TemplateProcessor getTemplateProcessor() {
            return templateProcessor;
        }
    }
}
