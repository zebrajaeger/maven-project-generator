package de.zebrajaeger.maven.projectgenerator.resources.path;

import de.zebrajaeger.maven.projectgenerator.utils.stringreplacer.StringReplacer;

import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class StringReplacerPathTransformer implements ResourcePathTransformer {

    private final StringReplacer replacer;

    public static StringReplacerPathTransformer of(StringReplacer value) {
        return new StringReplacerPathTransformer(value);
    }

    public StringReplacerPathTransformer(StringReplacer replacer) {
        this.replacer = replacer;
    }

    @Override
    public ResourcePath apply(ResourcePath resourcePath) {
        return ResourcePath.of(resourcePath.getPathParts().stream()
                .map(replacer::replace)
                .collect(Collectors.toList()));
    }

    public StringReplacer getReplacer() {
        return replacer;
    }
}
