package de.zebrajaeger.maven.projectgenerator.templateengine;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class TemplateContext {
    private String sourcePath;
    private Map<String, Object> context = new HashMap<>();

    public static TemplateContext of() {
        return new TemplateContext();
    }

    public static TemplateContext of(String sourcePath) {
        return new TemplateContext(sourcePath);
    }

    public static TemplateContext of(String sourcePath, Map<String, Object> value) {
        return new TemplateContext(sourcePath, value);
    }

    private TemplateContext() {
    }

    private TemplateContext(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    private TemplateContext(String sourcePath, Map<String, Object> context) {
        this.context.putAll(context);
        this.sourcePath = sourcePath;
    }

    public Object get(Object key) {
        return context.get(key);
    }

    public Object put(String key, Object value) {
        return context.put(key, value);
    }

    public Object remove(Object key) {
        return context.remove(key);
    }

    public void putAll(Map<? extends String, ?> m) {
        context.putAll(m);
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
