package de.zebrajaeger.maven.projectgenerator.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class StringList extends ArrayList<String> {
    public static StringList  of(String values){
        String[] parts = Objects.requireNonNull(values).split(",");
        StringList result = new StringList(parts.length);
        for(String part : parts){
            if(StringUtils.isNotBlank(part)){
                result.add(part.trim());
            }
        }
        return result;
    }

    public StringList(int initialCapacity) {
        super(initialCapacity);
    }
}
