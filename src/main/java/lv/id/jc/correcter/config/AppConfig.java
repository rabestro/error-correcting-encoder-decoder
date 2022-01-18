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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class AppConfig {
    @Value("${source.file:send.txt}")
    private String sourceFile;

    @Value("${encoded.file:encoded.txt}")
    private String encodedFile;

    @Value("${decoded.file:decoded.txt}")
    private String decodedFile;

    @Value("${received.file:received.txt}")
    private String receivedFile;

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
        var source = new DataInfo(sourceFile, List.of(getTextPrinter(), getHexPrinter(), getBinPrinter()));
        var target = new DataInfo(encodedFile, List.of(getHexPrinter(), getBinPrinter()));
        return new Transmitter(new HammingEncoder(), source, target);
    }

    public Runnable getSendAction() {
        var source = new DataInfo(encodedFile, List.of(getHexPrinter(), getBinPrinter()));
        var target = new DataInfo(receivedFile, List.of(getBinPrinter(), getHexPrinter()));
        return new Transmitter(new ErrorEmulator(), source, target);
    }

    public Runnable getDecodeAction() {
        var source = new DataInfo(receivedFile, List.of(getHexPrinter(), getBinPrinter()));
        var target = new DataInfo(decodedFile, List.of(getBinPrinter(), getHexPrinter(), getTextPrinter()));
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
