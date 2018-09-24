package de.zebrajaeger.maven.projectgenerator.templateengine;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.StringTemplateSource;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
@AutoService(TemplateEngine.class)
public class DefaultTemplateEngine implements TemplateEngine {
    public String convert(TemplateContext context, String template) throws TemplateEngineException {
        Handlebars handlebars = new Handlebars();
        try {
            handlebars.setCharset(StandardCharsets.UTF_8);
            Template t = handlebars.compile(new StringTemplateSource(
                    Objects.requireNonNull(context.getSourcePath()),
                    template)
            );
            return t.apply(context.getContext());

        } catch (IOException e) {
            throw new TemplateEngineException("WTF", e);
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
