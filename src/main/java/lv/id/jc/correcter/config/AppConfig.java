package lv.id.jc.correcter.config;

import lv.id.jc.correcter.Application;
import lv.id.jc.correcter.Transformation;
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
        var action = new Transformation();
        action.setCoder(new HammingEncoder());
        action.setInFile(SOURCE_FILE);
        action.setOutFile(ENCODED_FILE);
        action.setInPrinters(List.of(getTextPrinter(), getHexPrinter(), getBinPrinter()));
        action.setOutPrinters(List.of(getHexPrinter(), getBinPrinter()));
        return action;
    }

    public Runnable getSendAction() {
        var action = new Transformation();
        action.setCoder(new ErrorEmulator());
        action.setInFile(ENCODED_FILE);
        action.setOutFile(RECEIVED_FILE);
        action.setInPrinters(List.of(getHexPrinter(), getBinPrinter()));
        action.setOutPrinters(List.of(getBinPrinter(), getHexPrinter()));
        return action;
    }

    public Runnable getDecodeAction() {
        var action = new Transformation();
        action.setCoder(new HammingDecoder());
        action.setInFile(RECEIVED_FILE);
        action.setOutFile(DECODED_FILE);
        action.setInPrinters(List.of(getHexPrinter(), getBinPrinter()));
        action.setOutPrinters(List.of(getBinPrinter(), getHexPrinter(), getTextPrinter()));
        return action;
    }

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
