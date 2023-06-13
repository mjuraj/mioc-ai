package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextUtils {

    public static String trimText(String text) {
        boolean endsWithLink = text.matches(".*\\b(https?|ftp|file)://.*");
        boolean endsWithEmail = text.matches(".*\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b");
        if (endsWithLink || endsWithEmail) return text;

        Pattern pattern = Pattern.compile(".*[.?!]");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(0).trim();
        }

        return text;
    }
}
