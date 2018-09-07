package de.zebrajaeger.maven.projectgenerator.utils.stringreplacer;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public interface ReplacementFailStrategy {
    String replace(String token1, String key, String token2);
}
