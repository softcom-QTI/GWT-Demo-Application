package pro.softcom.archetype.gwt.client.util;

public class StringUtil {

    /**
     * Return true if the given String is either null or empty (e.g. "").
     *
     * @param s The String to check
     * @return True if the given String is null or empty, else false.
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    /**
     * Replace line breaks (\n) in the given text by &lt;/br&gt; tags.
     *
     * @param text The text containing line breaks to replace.
     * @return The corresponding text with &lt;/br&gt; tags instead of line breaks.
     */
    public static String replaceLineBreaks(String text) {
        return text.replaceAll("\n", "</br>");
    }
}
