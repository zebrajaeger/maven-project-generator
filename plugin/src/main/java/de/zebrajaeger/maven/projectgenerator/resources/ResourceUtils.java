package de.zebrajaeger.maven.projectgenerator.resources;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ResourceUtils {
    private ResourceManager resourceManager;

    public ResourceUtils(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void copy(ResourcePath path, ResourceFilter filter, File targetDirectory, boolean recursive) throws IOException {
        if (filter == null) {
            filter = new AcceptAllResourceFilter();
        }

        List<Item> items = resourceManager.getItems(path, recursive)
                .stream()
                .filter(filter)
                .collect(Collectors.toList());

        // for exception handling oldschool iterations
        for (Item i : items) {
            ResourcePath resourcePath = i.getPath().removeParent(path);
            File f = new File(targetDirectory, resourcePath.toString());
            if (i.isNode()) {
                f.mkdirs();
            } else if (i.isResource()) {
                FileUtils.writeByteArrayToFile(f, ((Resource) i).getContent());
            }
        }
    }
}
