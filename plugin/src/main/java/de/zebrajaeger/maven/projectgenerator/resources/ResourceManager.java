package de.zebrajaeger.maven.projectgenerator.resources;

import de.zebrajaeger.maven.projectgenerator.resources.model.Item;
import de.zebrajaeger.maven.projectgenerator.resources.model.Node;
import de.zebrajaeger.maven.projectgenerator.resources.model.Resource;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ResourceManager {
    private Node root = new Node();

    public void addSource(File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();
            LinkedList<String> path = new LinkedList<>();
            for (String s : name.replace('\\', '/').split("/")) {
                if (StringUtils.isNotBlank(s)) {
                    path.add(s);
                }
            }
            if (entry.isDirectory()) {
                root.add(path, new Node(path.getLast()));
            } else {
                byte[] content = IOUtils.toByteArray(zipFile.getInputStream(entry));
                root.add(path, new Resource(path.getLast(), content));
            }
        }
    }

    public Item getItem(ResourcePath path) {
        return root.getItem(path);
    }

    public List<Item> getItems(ResourcePath path, boolean recursive) {
        List<Item> result = new LinkedList<>();
        Item item = root.getItem(path);
        result.add(item);
        if (item.getClass().equals(Node.class)) {
            result.addAll(((Node) item).getItems(recursive));
        }
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
