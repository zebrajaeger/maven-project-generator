package de.zebrajaeger.maven.projectgenerator.query;


import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Queryer {
    private Prompter prompter = new Prompter();

    public boolean confirmConfiguration(Map<String, Object> config) throws PrompterException {
        StringBuilder query = new StringBuilder("Confirm properties configuration:\n");

        for (Map.Entry<String, Object> property : config.entrySet()) {
            query.append(property.getKey() + ": " + property.getValue() + "\n");
        }

        String answer = prompter.prompt(query.toString(), "Y");

        return "Y".equalsIgnoreCase(answer);
    }

    public String getPropertyValue(String requiredProperty, String defaultValue, Pattern validationRegex) throws PrompterException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("Define value for property '");
        queryBuilder.append(requiredProperty);
        queryBuilder.append('\'');

        if (validationRegex != null) {
            queryBuilder.append(" (should match expression '");
            queryBuilder.append(validationRegex);
            queryBuilder.append("')");
        }

        String query = queryBuilder.toString();
        String answer;
        boolean validAnswer = false;

        do {
            if ((defaultValue != null) && !defaultValue.equals("null")) {
                answer = prompter.prompt(query, defaultValue);
            } else {
                answer = prompter.prompt(query);
            }

            if (validationRegex == null || validationRegex.matcher(answer).matches()) {
                validAnswer = true;
            } else {
                query = "Value does not match the expression, please try again";
            }

        }
        while (!validAnswer);

        return answer;
    }
}
