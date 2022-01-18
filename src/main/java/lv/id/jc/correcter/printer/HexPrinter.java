package lv.id.jc.correcter.printer;

import java.util.HexFormat;

public class HexPrinter implements Printer {

    @Override
    public String apply(byte[] data) {
        var formatter = HexFormat.ofDelimiter(" ").withUpperCase();
        return "hex view: " + formatter.formatHex(data);
    }
}
