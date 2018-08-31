package de.zebrajaeger.maven.projectgenerator.resources;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedList;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public abstract class Item {
    private Node parent;
    private String name;

    public Item(Node parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public abstract boolean isResource();

    public abstract boolean isNode();

    public ResourcePath getPath() {
        LinkedList<String> path = new LinkedList<>();
        Item current = this;
        while (current != null) {
            path.addFirst(current.getName());
        }
        return new ResourcePath(path);
    }

    public String getName() {
        return name;
    }

    public Node getParent() {
        return parent;
    }

    protected void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
