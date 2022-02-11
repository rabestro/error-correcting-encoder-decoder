package lv.id.jc.correcter.printer;

import org.springframework.format.Printer;

import java.util.Locale;
import java.util.StringJoiner;

/**
 * Prints binary representation of data
 */
public class BinPrinter implements Printer<byte[]> {

    @Override
    public String print(byte[] data, Locale locale) {
        var binView = new StringJoiner(" ");
        for (var element : data) {
            binView.add(byteToBinary(element));
        }
        return "bin view: " + binView;
    }

    private String byteToBinary(byte data) {
        return "%8s".formatted(Integer.toBinaryString(Byte.toUnsignedInt(data))).replace(' ', '0');
    }

}
