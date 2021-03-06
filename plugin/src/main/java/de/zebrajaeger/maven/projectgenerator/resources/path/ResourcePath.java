package de.zebrajaeger.maven.projectgenerator.resources.path;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ResourcePath {
    private LinkedList<String> path;

    public static ResourcePath of(String... pathParts) {
        return new ResourcePath(pathParts);
    }

    public static ResourcePath of(List<String> pathParts) {
        return new ResourcePath(pathParts);
    }

    public static ResourcePath of(ResourcePath parent, String... pathParts) {
        return new ResourcePath(parent, pathParts);
    }

    public ResourcePath(List<String> pathParts) {
        this.path = new LinkedList<>(pathParts);
    }

    public ResourcePath(String... pathParts) {
        this.path = new LinkedList<>();
        Arrays.stream(pathParts).forEach(item -> path.add(item));
    }

    public ResourcePath(ResourcePath parent, String... pathParts) {
        this.path = new LinkedList<>();
        this.path.addAll(parent.path);
        Arrays.stream(pathParts).forEach(item -> path.add(item));
    }

    public ResourcePath addTail(String... pathParts) {
        return ResourcePath.of(this, pathParts);
    }

    public ResourcePath addHead(String... pathParts) {
        ResourcePath result = new ResourcePath(this);
        result.path.addAll(0, Arrays.asList(pathParts));
        return result;
    }

    public int getSize() {
        return path.size();
    }

    public String getFirst() {
        if (!path.isEmpty()) {
            return path.getFirst();
        }
        return null;
    }

    public String getLast() {
        if (!path.isEmpty()) {
            return path.getLast();
        }
        return null;
    }

    public ResourcePath withoutFirst() {
        if (path.isEmpty()) {
            return this;
        }

        return new ResourcePath(path.subList(1, path.size()));
    }

    public ResourcePath withoutLast() {
        if (path.isEmpty()) {
            return this;
        }

        return new ResourcePath(path.subList(0, path.size() - 2));
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public List<String> getPathParts(){
        return Collections.unmodifiableList(path);
    }

    public ResourcePath removeParent(ResourcePath parent) {
        if (path.size() < parent.getSize()) {
            throw new IllegalArgumentException(String.format("my path('%s') doesn't starts mit parentPath('%s')", this, parent));
        }

        LinkedList<String> newPath = new LinkedList<>(path);
        ArrayList<String> parentPath = new ArrayList<>(parent.path);
        for (int i = 0; i < parentPath.size(); ++i) {
            if (!newPath.getFirst().equals(parentPath.get(i))) {
                throw new IllegalArgumentException(String.format("my path('%s') doesn't starts mit parentPath('%s')", this, parent));
            }
            newPath.removeFirst();
        }

        return new ResourcePath(newPath);
    }

    @Override
    public String toString() {
        return StringUtils.join(path, "/");
    }
}
