package de.zebrajaeger.maven.projectgenerator.templateengine;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TemplateProcessor {
    private TemplateEngine templateEngine;
    private TemplateContext templateContext;

    public TemplateProcessor(TemplateEngine templateEngine, TemplateContext templateContext) {
        this.templateEngine = templateEngine;
        this.templateContext = templateContext;
    }

    public String convert(String template) throws TemplateEngineException {
        return templateEngine.convert(templateContext, template);
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public TemplateContext getTemplateContext() {
        return templateContext;
    }

    public void setTemplateContext(TemplateContext templateContext) {
        this.templateContext = templateContext;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
