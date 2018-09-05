package de.zebrajaeger.maven.projectgenerator.project;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Property extends RequiredProperty {
    private String value;

    public Property(RequiredProperty requiredProperty) {
        super(requiredProperty);
    }

    public Property(RequiredProperty requiredProperty, String value) {
        super(requiredProperty);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
