package {{package}}.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class JspElFunctions{

    private JspElFunctions() {
    }

    public static String getHostId(boolean linefeed) {
        String result = "unknown";
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] parts = hostName.split("\\.");
            if (parts.length > 0) {
                result = parts[0];
            }
        } catch (UnknownHostException e) {
            return "unknown(" + e.getMessage() + ")";
        }
        return (linefeed) ? result + "\n" : result;
    }
}
