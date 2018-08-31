package de.zebrajaeger.maven.projectgenerator.templateengine.impl;

import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateContext;
import de.zebrajaeger.maven.projectgenerator.templateengine.TemplateEngine;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
//@AutoService(TemplateEngine.class)
public class JtwigTemplateEngine implements TemplateEngine {
    public String convert(TemplateContext context, String template) {
        JtwigModel jtwigModel = JtwigModel.newModel(context.getContext());
        return JtwigTemplate.inlineTemplate(template).render(jtwigModel);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
