package de.zebrajaeger.maven.projectgenerator.templateengine;

public class DefaultTemplateProcessor extends TemplateProcessor {

    public static DefaultTemplateProcessor of() {
        return new DefaultTemplateProcessor(new TemplateContext());
    }

    public static DefaultTemplateProcessor of(TemplateContext templateContext) {
        return new DefaultTemplateProcessor(templateContext);
    }

    public DefaultTemplateProcessor(TemplateContext templateContext) {
        super(new DefaultTemplateEngine(), templateContext);
    }
}
