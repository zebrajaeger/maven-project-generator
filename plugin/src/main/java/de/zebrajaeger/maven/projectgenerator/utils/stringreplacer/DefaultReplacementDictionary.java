package de.zebrajaeger.maven.projectgenerator.utils.stringreplacer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class DefaultReplacementDictionary implements ReplacementDictionary {

    private Map<String, Object> replacements = new HashMap<>();

    public static DefaultReplacementDictionary of() {
        return new DefaultReplacementDictionary();
    }

    public static DefaultReplacementDictionary of(String key, Object value) {
        DefaultReplacementDictionary result = new DefaultReplacementDictionary();
        result.replacement(key, value);
        return result;
    }

    public static DefaultReplacementDictionary of(Map<String, Object> value) {
        DefaultReplacementDictionary result = new DefaultReplacementDictionary();
        result.replacements(value);
        return result;
    }

    public DefaultReplacementDictionary replacement(String key, Object value) {
        replacements.put(key, value);
        return this;
    }

    public DefaultReplacementDictionary replacements(Map<String, Object> value) {
        replacements.putAll(value);
        return this;
    }

    @Override
    public Optional<String> find(String key) {
        Object value = replacements.get(key);
        String result = value == null ? null : value.toString();
        return Optional.ofNullable(result);
    }
}
