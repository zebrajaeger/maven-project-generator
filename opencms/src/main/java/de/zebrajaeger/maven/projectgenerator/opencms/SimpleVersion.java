package de.zebrajaeger.maven.projectgenerator.opencms;

import org.apache.maven.plugin.MojoExecutionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class SimpleVersion {

    public static final String PATTERN = "(\\d+(.\\d+){0,2}).*";
    private String version;

    public SimpleVersion(String version) {
        this.version = version;
    }

    public static SimpleVersion of(String value) throws MojoExecutionException {
        Matcher matcher = Pattern.compile(PATTERN).matcher(value);
        if(!matcher.matches()){
            String msg = String.format("Can not convert version '%s' to with Pattern '%s'", value, PATTERN);
            throw new MojoExecutionException(msg);
        }

        return new SimpleVersion(matcher.group(1));
    }

    @Override
    public String toString() {
        return version;
    }
}
