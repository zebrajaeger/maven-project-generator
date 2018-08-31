package de.zebrajaeger.maven.projectgenerator.templateengine;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class TemplateContext {
    private Map<String,Object> context = new HashMap<>();

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
