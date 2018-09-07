package de.zebrajaeger.maven.projectgenerator.resources.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class FilterChain implements ResourceFilter {
    private Optional<Boolean> defaultDecision;
    private List<ResourceFilter> filters = new LinkedList<>();

    public static FilterChain of(boolean defaultDecision) {
        return new FilterChain(defaultDecision);
    }

    public static FilterChain of(boolean defaultDecision, List<ResourceFilter> values) {
        return new FilterChain(defaultDecision, values);
    }

    public static FilterChain of(boolean defaultDecision, ResourceFilter... values) {
        return of(defaultDecision, Arrays.asList(values));
    }

    private FilterChain(boolean defaultDecision) {
        this.defaultDecision = Optional.of(defaultDecision);
    }

    private FilterChain(boolean defaultDecision, List<ResourceFilter> filters) {
        this(defaultDecision);
        this.filters = filters;
    }

    public Optional<Boolean> accept(Item item) {
        for (ResourceFilter f : filters) {
            Optional<Boolean> accept = f.accept(item);
            if (accept.isPresent()) {
                return accept;
            }
        }
        return defaultDecision;
    }

    public boolean acceptItem(Item item) {
        return accept(item).orElse(false);
    }
}
