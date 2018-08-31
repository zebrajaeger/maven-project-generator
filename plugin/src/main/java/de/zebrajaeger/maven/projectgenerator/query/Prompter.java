package de.zebrajaeger.maven.projectgenerator.query;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Prompter {

    public String prompt(String message) throws PrompterException {
        writePrompt(message);

        return readLine();
    }

    public String prompt(String message, String defaultReply) throws PrompterException {
        writePrompt(formatMessage(message, null, defaultReply));

        String line = readLine();

        if (StringUtils.isEmpty(line)) {
            line = defaultReply;
        }

        return line;
    }

    public String prompt(String message, List<String> possibleValues, String defaultReply) throws PrompterException {
        String formattedMessage = formatMessage(message, possibleValues, defaultReply);

        String line;

        do {
            writePrompt(formattedMessage);

            line = readLine();

            if (StringUtils.isEmpty(line)) {
                line = defaultReply;
            }

            if (line != null && !possibleValues.contains(line)) {
                System.out.println("Invalid selection.");
            }
        }
        while (line == null || !possibleValues.contains(line));

        return line;
    }

    private String formatMessage(String message, List<String> possibleValues, String defaultReply) {
        StringBuffer formatted = new StringBuffer(message.length() * 2);

        formatted.append(message);

        if (possibleValues != null && !possibleValues.isEmpty()) {
            formatted
                    .append(" (")
                    .append(StringUtils.join(possibleValues, '/'))
                    .append(')');
        }

        if (defaultReply != null) {
            formatted.append(defaultReply);
        }

        return formatted.toString();
    }

    private void writePrompt(String message) {
        showMessage(message + ": ");
    }

    private String readLine() throws PrompterException {
        BufferedReader consoleReader = null;
        try {
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            return consoleReader.readLine();
        } catch (IOException e) {
            throw new PrompterException("Failed to read user response", e);
        }
    }

    public void showMessage(String message) {
        System.out.print(message);
    }
}
