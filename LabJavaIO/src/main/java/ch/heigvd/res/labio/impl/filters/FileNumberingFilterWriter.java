package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int j = 1;
    boolean charBeforeWasCtrl = false;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        StringBuilder tmp;
        tmp = new StringBuilder(str);
        tmp.append("\n");
        str = tmp.substring(off, off + len);

        StringBuilder result = new StringBuilder();

        if (j == 1) {
            tmp = new StringBuilder(str);
            tmp.insert(0, j++);
            tmp.insert(1, '\t');
            str = tmp.toString();
        }

        Pattern newLine = Pattern.compile("\n|\r\n|\r");
        Matcher m = newLine.matcher(str);

        while (m.find()) {
            result = result.append(str.substring(0, m.end()));
            str = new String(str.substring(m.end(), str.length()));
            result.append(j++ + "\t");
            m = newLine.matcher(str);
        }

        if (!str.isEmpty()) {
            result.append(str);
        }
        System.out.println(result.toString());
        super.write(result.toString(), 0, result.toString().length());
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        // Source : https://stackoverflow.com/questions/7655127/how-to-convert-a-char-array-back-to-a-string
        this.write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        StringBuilder result = new StringBuilder();

        if (j == 1) {
            super.write(j++ + "\t" + (char) c);
            return;
        }

        if (c == '\n' && !charBeforeWasCtrl) {
            super.write('\n');
            super.write(String.valueOf(j++));
            super.write('\t');
            return;
        } else if (c == '\r') {
            charBeforeWasCtrl = true;
            super.write((char) c);
            return;
        } else if (charBeforeWasCtrl) {

            if (c == '\n') {
                //System.out.println("i: " + i + " j = " + j + " Result : " + result.toString() + " Original :" + (char) c);
                charBeforeWasCtrl = false;

                super.write('\n');
                super.write(String.valueOf(j++));
                super.write('\t');

                return;
            } else {
                charBeforeWasCtrl = false;

                super.write(String.valueOf(j++));
                super.write('\t');
                super.write((char) c);
            }
        } else {
            super.write((char) c);
            return;
        }

    }

}
