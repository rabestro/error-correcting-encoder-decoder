package lv.id.jc.correcter.printer;

import java.util.StringJoiner;

public class HexPrinter implements Printer {

    @Override
    public String apply(byte[] data) {
        var view = new StringJoiner(" ");
        for (var element : data) {
            view.add(String.format("%X", element));
        }
        return "hex view: " + view;
    }
}
