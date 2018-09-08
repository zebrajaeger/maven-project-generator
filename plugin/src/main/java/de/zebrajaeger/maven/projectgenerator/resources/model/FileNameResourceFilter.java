package de.zebrajaeger.maven.projectgenerator.resources.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class FileNameResourceFilter implements ResourceFilter {

    private boolean accept;
    private boolean caseSensitive;
    private Set<String> matchingFileNames;

    public static FileNameResourceFilter of(boolean accept, String... extensions) {
        return of(accept, true, extensions);
    }

    public static FileNameResourceFilter of(boolean accept, boolean caseSensitive, String... extensions) {
        Set<String> matchingExtension;
        if (caseSensitive) {
            matchingExtension = Arrays.stream(extensions)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        } else {
            matchingExtension = Arrays.stream(extensions).collect(Collectors.toSet());
        }
        return new FileNameResourceFilter(accept, false, matchingExtension);
    }

    private FileNameResourceFilter(boolean accept, boolean caseSensitive, Set<String> matchingFileNames) {
        this.accept = accept;
        this.caseSensitive = caseSensitive;
        this.matchingFileNames = matchingFileNames;
    }

    @Override
    public Optional<Boolean> accept(Item item) {
        String name = item.getName();
        if (!caseSensitive) {
            name = name.toLowerCase();
        }

        return matchingFileNames.contains(name) ? Optional.of(accept) : Optional.empty();
    }
}
