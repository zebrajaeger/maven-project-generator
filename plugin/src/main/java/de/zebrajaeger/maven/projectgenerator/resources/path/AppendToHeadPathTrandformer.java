package de.zebrajaeger.maven.projectgenerator.resources.path;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class AppendToHeadPathTrandformer implements ResourcePathTransformer {
    private List<String> headPathParts;

    public static AppendToHeadPathTrandformer of(String... parts) {
        return new AppendToHeadPathTrandformer(parts);
    }

    public AppendToHeadPathTrandformer(String... parts) {
        this.headPathParts = Arrays.asList(parts);
    }

    @Override
    public ResourcePath apply(ResourcePath resourcePath) {
        List<String> parts = new LinkedList<>();
        parts.addAll(headPathParts);
        parts.addAll(resourcePath.getPathParts());
        return ResourcePath.of(parts);
    }
}
