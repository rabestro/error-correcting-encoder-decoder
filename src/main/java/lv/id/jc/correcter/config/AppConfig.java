package lv.id.jc.correcter.config;

import lv.id.jc.correcter.Application;
import lv.id.jc.correcter.DataInfo;
import lv.id.jc.correcter.Transmitter;
import lv.id.jc.correcter.coder.ErrorEmulator;
import lv.id.jc.correcter.coder.HammingDecoder;
import lv.id.jc.correcter.coder.HammingEncoder;
import lv.id.jc.correcter.printer.BinPrinter;
import lv.id.jc.correcter.printer.HexPrinter;
import lv.id.jc.correcter.printer.Printer;
import lv.id.jc.correcter.printer.TextPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class AppConfig {
    private static final String SOURCE_FILE = "send.txt";
    private static final String ENCODED_FILE = "encoded.txt";
    private static final String DECODED_FILE = "decoded.txt";
    private static final String RECEIVED_FILE = "received.txt";

    @Bean("textPrinter")
    public Printer getTextPrinter() {
        return new TextPrinter();
    }

    @Bean("hexPrinter")
    public Printer getHexPrinter() {
        return new HexPrinter();
    }

    @Bean("binPrinter")
    public Printer getBinPrinter() {
        return new BinPrinter();
    }

    public Runnable getEncodeAction() {
        var source = new DataInfo(SOURCE_FILE, List.of(getTextPrinter(), getHexPrinter(), getBinPrinter()));
        var target = new DataInfo(ENCODED_FILE, List.of(getHexPrinter(), getBinPrinter()));
        return new Transmitter(new HammingEncoder(), source, target);
    }

    public Runnable getSendAction() {
        var source = new DataInfo(ENCODED_FILE, List.of(getHexPrinter(), getBinPrinter()));
        var target = new DataInfo(RECEIVED_FILE, List.of(getBinPrinter(), getHexPrinter()));
        return new Transmitter(new ErrorEmulator(), source, target);
    }

    public Runnable getDecodeAction() {
        var source = new DataInfo(RECEIVED_FILE, List.of(getHexPrinter(), getBinPrinter()));
        var target = new DataInfo(DECODED_FILE, List.of(getBinPrinter(), getHexPrinter(), getTextPrinter()));
        return new Transmitter(new HammingDecoder(), source, target);
    }

    @Bean("commands")
    public Map<String, Runnable> getCommands() {
        return Map.of(
                "encode", getEncodeAction(),
                "send", getSendAction(),
                "decode", getDecodeAction()
        );
    }

    @Bean("application")
    public Runnable getApplication() {
        return new Application(getCommands());
    }
}
