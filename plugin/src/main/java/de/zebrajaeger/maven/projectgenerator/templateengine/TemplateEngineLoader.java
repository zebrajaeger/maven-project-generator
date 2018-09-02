package de.zebrajaeger.maven.projectgenerator.templateengine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class TemplateEngineLoader {
    public static List<TemplateEngine> load() {
        List<TemplateEngine> result = new LinkedList<>();
        ServiceLoader<TemplateEngine> sl = ServiceLoader.load(TemplateEngine.class);
        Iterator<TemplateEngine> iterator = sl.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    /**
     * load another implementation or - if not exeists - the default one
     * @return a instance or null
     */
    public static TemplateEngine loadOne() {
        List<TemplateEngine> load = load();
        if (load.isEmpty()) {
            return null;
        }

        if (load.size() == 1) {
            return load.get(0);
        }

        for (TemplateEngine e : load) {
            if (e.getName().equals(DefaultTemplateEngine.class.getName())) {
                continue;
            }
            return e;
        }

        return null;
    }

    /**
     * load template engine with given name
     * @param name name of template engine
     * @return template engine or nuöö
     */
    public static TemplateEngine load(String name) {
        List<TemplateEngine> load = load();
        for (TemplateEngine e : load) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }
}
