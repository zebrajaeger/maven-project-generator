package de.zebrajaeger.maven.projectgenerator.jar;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public abstract class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public abstract boolean isResource();
    public abstract boolean isNode();

    public String getName() {
        return name;
    }
}
