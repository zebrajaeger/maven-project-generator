package de.zebrajaeger.maven.projectgenerator.resources.model;

import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class FileExtensionResourceFilter implements ResourceFilter {

    private boolean accept;
    private boolean caseSensitive;
    private Set<String> matchingExtension;

    public static FileExtensionResourceFilter of(boolean accept, String... extensions) {
        return of(accept, true, extensions);
    }

    public static FileExtensionResourceFilter of(boolean accept, boolean caseSensitive, String... extensions) {
        Set<String> matchingExtension;
        if (caseSensitive) {
            matchingExtension = Arrays.stream(extensions)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        } else {
            matchingExtension = Arrays.stream(extensions).collect(Collectors.toSet());
        }
        return new FileExtensionResourceFilter(accept, false, matchingExtension);
    }

    private FileExtensionResourceFilter(boolean accept, boolean caseSensitive, Set<String> matchingExtension) {
        this.accept = accept;
        this.caseSensitive = caseSensitive;
        this.matchingExtension = matchingExtension;
    }

    @Override
    public Optional<Boolean> accept(Item item) {
        String name = item.getName();
        String extension = FilenameUtils.getExtension(name);
        if (!caseSensitive) {
            extension = extension.toLowerCase();
        }

        return matchingExtension.contains(extension) ? Optional.of(accept) : Optional.empty();
    }
}
