package de.zebrajaeger.maven.projectgenerator.utils.stringreplacer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class StringReplacer {

    private ReplacementFailStrategy replacementFailStrategy = new ReplaceWithOriginalFailStrategy();
    private ReplacementDictionary dictionary = new DefaultReplacementDictionary();

    private boolean isOpen;
    private List<String> result = new LinkedList<>();
    private List<String> temp = new LinkedList<>();

    public static StringReplacer of() {
        return new StringReplacer();
    }

    public static StringReplacer of(ReplacementDictionary dictionary) {
        StringReplacer result = new StringReplacer();
        result.dictionary(dictionary);
        return result;
    }

    public static StringReplacer of(ReplacementFailStrategy strategy) {
        StringReplacer result = new StringReplacer();
        result.failStrategy(strategy);
        return result;
    }

    public static StringReplacer of(ReplacementDictionary dictionary, ReplacementFailStrategy strategy) {
        StringReplacer result = new StringReplacer();
        result.dictionary(dictionary);
        result.failStrategy(strategy);
        return result;
    }

    public StringReplacer failStrategy(ReplacementFailStrategy value) {
        replacementFailStrategy = value;
        return this;
    }

    public StringReplacer dictionary(ReplacementDictionary value) {
        dictionary = value;
        return this;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    private void handleToken(String token) {
        if (!isOpen()) {
            if ("{{".equals(token)) {
                temp.add(token);
                setOpen(true);
            } else {
                result.add(token);
            }
        } else {
            if ("{{".equals(token)) {
                result.addAll(temp);
                temp.clear();
                temp.add(token);
            } else if ("}}".equals(token)) {
                if (temp.size() == 2) {
                    String key = temp.get(1);
                    String replacement = dictionary
                            .find(key)
                            .orElseGet(() ->
                                    replacementFailStrategy.replace(temp.get(0), key, token));
                    if (replacement != null) {
                        result.add(replacement);
                    }
                    temp.clear();
                    setOpen(false);
                } else {
                    result.addAll(temp);
                    temp.clear();
                    result.add(token);
                }
            } else {
                temp.add(token);
            }
        }
    }

    public String replace(String text) {
        isOpen = false;
        temp.clear();
        result.clear();

        for (String s : text.split(createRegex("\\{\\{", "\\}\\}"))) {
            handleToken(s);
        }
        result.addAll(temp);
        setOpen(false);
        return result.stream().collect(Collectors.joining());
    }

    private String createRegex(String... delimiters) {
        String parts = Arrays.stream(delimiters)
                .map(delimiter -> String.format("(?<=%s)|(?=%s)", delimiter, delimiter))
                .collect(Collectors.joining("|"));
        return "(" + parts + ")";
    }
}
