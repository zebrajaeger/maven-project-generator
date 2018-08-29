package de.zebrajaeger.maven.projectgenerator.jar;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
