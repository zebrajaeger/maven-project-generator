package de.zebrajaeger.maven.projectgenerator.resources.path;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class RegexReplaceResourcePathTransformer implements ResourcePathTransformer {
    private Pattern regex;
    private String replacement;

    public static RegexReplaceResourcePathTransformer of(String regex, String replacement) {
        return new RegexReplaceResourcePathTransformer(regex, replacement);
    }

    public static RegexReplaceResourcePathTransformer of(Pattern regex, String replacement) {
        return new RegexReplaceResourcePathTransformer(regex, replacement);
    }

    private RegexReplaceResourcePathTransformer(String regex, String replacement) {
        this.regex = Pattern.compile(regex);
        this.replacement = replacement;
    }

    private RegexReplaceResourcePathTransformer(Pattern regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ResourcePath apply(ResourcePath resourcePath) {
        List<String> result = new LinkedList<>();
        for (String part : resourcePath.getPathParts()) {
            if (regex.matcher(part).matches()) {
                result.add(replacement);
            } else {
                result.add(part);
            }
        }
        return ResourcePath.of(result);
    }
}
