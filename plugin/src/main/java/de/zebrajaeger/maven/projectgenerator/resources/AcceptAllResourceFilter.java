package de.zebrajaeger.maven.projectgenerator.resources;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class AcceptAllResourceFilter implements ResourceFilter {
    @Override
    public boolean test(Item item) {
        return true;
    }
}
