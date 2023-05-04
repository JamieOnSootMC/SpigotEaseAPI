package dev.jamieisgeek;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTranslator {
    private static final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final char COLOUR_CHAR = ChatColor.COLOR_CHAR;
    /*
        @param string The string to translate to hex
    */
    public static String translateToHex(String string) {
        Matcher matcher = hexPattern.matcher(string);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOUR_CHAR + "x"
                    + COLOUR_CHAR + group.charAt(0) + COLOUR_CHAR + group.charAt(1)
                    + COLOUR_CHAR + group.charAt(2) + COLOUR_CHAR + group.charAt(3)
                    + COLOUR_CHAR + group.charAt(4) + COLOUR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }
}
