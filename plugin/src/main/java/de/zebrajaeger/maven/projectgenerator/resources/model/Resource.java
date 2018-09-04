package de.zebrajaeger.maven.projectgenerator.resources.model;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Resource extends Item {
    private byte[] content;

    public Resource(String name, byte[] content) {
        super(null, name);
        this.content = content;
    }

    public Resource(Node parent, String name, byte[] content) {
        super(parent, name);
        this.content = content;
    }

    @Override
    public boolean isResource() {
        return true;
    }

    @Override
    public boolean isNode() {
        return false;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    public byte[] getContent() {
        return content;
    }

}
