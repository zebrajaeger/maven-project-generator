package de.zebrajaeger.maven.projectgenerator.resources.model;

import java.util.function.Predicate;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public interface ResourceFilter {
    Boolean accept(Item item);
}
