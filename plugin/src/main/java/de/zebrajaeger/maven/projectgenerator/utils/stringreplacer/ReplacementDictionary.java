package de.zebrajaeger.maven.projectgenerator.utils.stringreplacer;

import java.util.Optional;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public interface ReplacementDictionary {
    Optional<String> find(String key);
}
