package de.zebrajaeger.maven.projectgenerator.templateengine;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
//@AutoService(TemplateEngine.class)
public class DefaultTemplateEngine implements TemplateEngine {
    public String convert(TemplateContext context, String template) throws TemplateEngineException {
        Handlebars handlebars = new Handlebars();
        try {
            handlebars.setCharset(StandardCharsets.UTF_8);
            Template t = handlebars.compileInline(template);
            return t.apply(context.getContext());

        } catch (IOException e) {
            throw new TemplateEngineException("WTF", e);
        }

//        JtwigModel jtwigModel = JtwigModel.newModel(context.getContext());
//        try {
//            return JtwigTemplate.inlineTemplate(template).render(jtwigModel);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return "";
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
