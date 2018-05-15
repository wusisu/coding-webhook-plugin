package net.coding.jenkins.plugin.oauth;

public class CodingUtil {
    public static String fixEndwithSlash(String uri) {
        if (uri == null || uri.length() == 0) {
            return "";
        }
        if (uri.endsWith("/")) {
            return uri.substring(0, uri.length() - 1);
        }
        return uri;
    }
}
