package de.zebrajaeger.maven.projectgenerator.resources.model;

import java.util.Optional;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public interface ResourceFilter {
    Optional<Boolean> accept(Item item);
}
