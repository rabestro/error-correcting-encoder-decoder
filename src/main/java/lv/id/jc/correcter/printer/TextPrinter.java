package lv.id.jc.correcter.printer;

/**
 * Prints text representation of data
 */
public class TextPrinter implements Printer {
    @Override
    public String apply(byte[] data) {
        return "text view: " + new String(data);
    }
}
