package de.zebrajaeger.maven.projectgenerator.resources.path;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class RegexReplaceRecourcePathTransformer implements ResourcePathTransformer {
    private Pattern regex;
    private String replacement;

    public static RegexReplaceRecourcePathTransformer of(String regex, String replacement) {
        return new RegexReplaceRecourcePathTransformer(regex, replacement);
    }

    public static RegexReplaceRecourcePathTransformer of(Pattern regex, String replacement) {
        return new RegexReplaceRecourcePathTransformer(regex, replacement);
    }

    private RegexReplaceRecourcePathTransformer(String regex, String replacement) {
        this.regex = Pattern.compile(regex);
        this.replacement = replacement;
    }

    private RegexReplaceRecourcePathTransformer(Pattern regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ResourcePath transform(ResourcePath resourcePath) {
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
