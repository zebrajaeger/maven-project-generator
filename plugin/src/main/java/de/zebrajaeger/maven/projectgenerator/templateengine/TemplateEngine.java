package de.zebrajaeger.maven.projectgenerator.templateengine;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public interface TemplateEngine {
    String convert(TemplateContext context, String template);
    String getName();
}
