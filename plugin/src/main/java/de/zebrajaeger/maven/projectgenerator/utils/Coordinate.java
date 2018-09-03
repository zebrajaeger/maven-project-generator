package de.zebrajaeger.maven.projectgenerator.utils;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Coordinate {
    private String groupId;
    private String artifactId;
    private String version;

    public static Coordinate of(String coordinate) {
        String[] parts = Objects.requireNonNull(coordinate).split(":");
        if (parts.length != 3) {
            String msg = String.format("A coordinate consist of three parts but we have only '%s': '%s'", parts.length, coordinate);
            throw new IllegalArgumentException(msg);
        }

        if (StringUtils.isBlank(parts[0])) {
            String msg = String.format("GroupId can not be blank: '%s'", coordinate);
            throw new IllegalArgumentException(msg);
        }
        checkForDots(parts[0]);
        if (StringUtils.isBlank(parts[1])) {
            String msg = String.format("ArtifactId can not be blank: '%s'", coordinate);
            throw new IllegalArgumentException(msg);
        }
        checkForDots(parts[1]);
        if (StringUtils.isBlank(parts[2])) {
            String msg = String.format("Version can not be blank: '%s'", coordinate);
            throw new IllegalArgumentException(msg);
        }
        checkForDots(parts[2]);

        return new Coordinate(parts[0], parts[1], parts[2]);
    }

    private static void checkForDots(String value) {
        if (value.startsWith(".") || value.endsWith(".")) {
            String msg = String.format("cannot start or end with dot '.': '%s'", value);
            throw new IllegalArgumentException(msg);
        }
    }

    public Coordinate(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
