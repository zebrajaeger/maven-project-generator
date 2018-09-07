package de.zebrajaeger.maven.projectgenerator.utils.stringreplacer;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ReplaceWithOriginalFailStrategy implements ReplacementFailStrategy {
    @Override
    public String replace(String token1, String key, String token2) {
        return token1 + key + token2;
    }
}
