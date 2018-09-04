package de.zebrajaeger.maven.projectgenerator.resources.model;

import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;
import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePathTransformer;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class DefaultResourcePathTransformer implements ResourcePathTransformer {
    @Override
    public ResourcePath transform(ResourcePath resourcePath) {
        return resourcePath;
    }
}
