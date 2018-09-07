package de.zebrajaeger.maven.projectgenerator.resources.model;

import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePathTransformer;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class DefaultResourcePathTransformer implements ResourcePathTransformer {

    @Override
    public ResourcePath apply(ResourcePath resourcePath) {
        return resourcePath;
    }
}
