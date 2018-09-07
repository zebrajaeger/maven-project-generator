package de.zebrajaeger.maven.projectgenerator.resources.path;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class PackagePathTrandformer implements ResourcePathTransformer {
    private List<String> packageParts;

    public static PackagePathTrandformer of(String pckge) {
        return new PackagePathTrandformer(pckge.split("\\."));
    }

    public PackagePathTrandformer(String... parts) {
        this.packageParts = Arrays.asList(parts);
    }

    @Override
    public ResourcePath apply(ResourcePath resourcePath) {
        List<String> parts = new LinkedList<>();
        for (String p : resourcePath.getPathParts()) {
            if ("{{package}}".equals(p)) {
                parts.addAll(packageParts);
            } else {
                parts.add(p);
            }
        }
        return ResourcePath.of(parts);
    }
}
