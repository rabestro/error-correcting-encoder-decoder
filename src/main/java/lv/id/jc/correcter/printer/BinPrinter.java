package lv.id.jc.correcter.printer;

import java.util.StringJoiner;

/**
 * Prints binary representation of data
 */
public class BinPrinter implements Printer {

    @Override
    public String apply(byte[] data) {
        var binView = new StringJoiner(" ");
        for (var element : data) {
            binView.add(byteToBinary(element));
        }
        return "bin view: " + binView;
    }

    private String byteToBinary(byte data) {
        return String
                .format("%8s", Integer.toBinaryString(Byte.toUnsignedInt(data)))
                .replace(' ', '0');
    }

}
