package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the argument does not
     * contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
        String[] result = {"", ""};

        int i = 0;

        // Source : https://stackoverflow.com/questions/8923398/regex-doesnt-work-in-string-matches
        Pattern newLine = Pattern.compile("\n|\r\n|\r");
        Matcher m = newLine.matcher(lines);

        if (m.find()) {
            // Source : https://www.tutorialspoint.com/java/java_string_split.htm
            for (String s : lines.split("\n|\r\n|\r")) {
                StringBuilder test = new StringBuilder(s);

                System.out.println(i);
                System.out.println(s);

                if (lines.contains("\r\n")) {
                    result[i++] = test.append("\r\n").toString();
                } else if (lines.contains("\n")) {
                    result[i++] = test.append("\n").toString();
                } else if (lines.contains("\r")) {
                    result[i++] = test.append("\r").toString();
                }
            }
        } else {
            result[1] = lines;
        }


        return result;
    }

}
