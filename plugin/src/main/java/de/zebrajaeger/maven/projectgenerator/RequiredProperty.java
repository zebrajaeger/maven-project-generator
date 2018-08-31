package de.zebrajaeger.maven.projectgenerator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.regex.Pattern;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class RequiredProperty {
    private String name;
    private String defaultValue = null;
    private Pattern validation = null;

    public RequiredProperty(String name) {
        this.name = name;
    }

    public RequiredProperty(String name, String defaultValue, Pattern validation) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.validation = validation;
    }

    public RequiredProperty(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.validation = validation;
    }

    public RequiredProperty(String name, Pattern validation) {
        this.name = name;
        this.validation = validation;
    }

    public RequiredProperty(RequiredProperty requiredProperty) {
        this.name = requiredProperty.getName();
        this.defaultValue = requiredProperty.getDefaultValue();
        this.validation = requiredProperty.getValidation();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Pattern getValidation() {
        return validation;
    }

    public void setValidation(Pattern validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
