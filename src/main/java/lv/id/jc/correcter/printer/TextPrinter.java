package lv.id.jc.correcter.printer;

import org.springframework.format.Printer;

import java.util.Locale;

/**
 * Prints text representation of data
 */
public class TextPrinter implements Printer<byte[]> {

    @Override
    public String print(byte[] data, Locale locale) {
        return "text view: " + new String(data);
    }
}
