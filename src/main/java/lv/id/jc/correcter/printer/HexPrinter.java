package lv.id.jc.correcter.printer;

import org.springframework.format.Printer;

import java.util.HexFormat;
import java.util.Locale;

public class HexPrinter implements Printer<byte[]> {

    @Override
    public String print(byte[] data, Locale locale) {
        var formatter = HexFormat.ofDelimiter(" ").withUpperCase();
        return "hex view: " + formatter.formatHex(data);
    }
}
