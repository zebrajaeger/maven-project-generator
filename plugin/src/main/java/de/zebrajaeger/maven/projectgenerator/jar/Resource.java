package de.zebrajaeger.maven.projectgenerator.jar;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Resource extends Item {
    public Resource(String name) {
        super(name);
    }

    @Override
    public boolean isResource() {
        return true;
    }

    @Override
    public boolean isNode() {
        return false;
    }
}
