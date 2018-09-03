package ${package}.common;

import java.util.Base64;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Common {

    private Common() {

    }

    public static String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input));
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
