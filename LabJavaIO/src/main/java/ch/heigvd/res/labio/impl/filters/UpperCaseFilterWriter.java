package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {

    public UpperCaseFilterWriter(Writer wrappedWriter) {
        super(wrappedWriter);
    }

    @Override
    public void write(String str, int off, int len) throws IOException, IllegalArgumentException {
        if (off > str.length() || off < 0 || len > str.length() || len < 0 || off + len > str.length()) {
            throw new IllegalArgumentException();
        }

        String result = str.substring(0, off) + str.substring(off, off + len).toUpperCase();
        super.write(result, off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        // Source : https://stackoverflow.com/questions/7655127/how-to-convert-a-char-array-back-to-a-string
        super.write(new String(cbuf).toUpperCase(), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        super.write(Character.toUpperCase(c));
    }

}
