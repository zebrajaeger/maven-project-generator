package de.zebrajaeger.maven.projectgenerator.jar;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Resource extends Item {
    private byte[] content;

    public Resource(String name, byte[] content) {
        super(name);
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

    public byte[] getContent() {
        return content;
    }
}
