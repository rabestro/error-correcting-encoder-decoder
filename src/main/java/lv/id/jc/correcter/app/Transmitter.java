package lv.id.jc.correcter.app;

import lv.id.jc.correcter.coder.Coder;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.Logger.Level.WARNING;

public record Transmitter(Coder coder, DataConfig source, DataConfig target) implements Runnable {
    private static final System.Logger LOGGER = System.getLogger("Transmitter");

    @Override
    public void run() {
        try {
            var data = readData();
            printInfo(source, data);

            var coded = coder.apply(data);

            printInfo(target, coded);
            writeData(coded);
        } catch (IOException e) {
            LOGGER.log(WARNING, e);
        }
    }

    @SuppressWarnings("squid:S106")
    void printInfo(DataConfig dataConfig, byte[] data) {
        System.out.println(dataConfig.file());
        dataConfig.printers().forEach(printer -> System.out.println(printer.apply(data)));
    }

    byte[] readData() throws IOException {
        return Files.readAllBytes(source.file());
    }

    void writeData(byte[] data) throws IOException {
        Files.write(target.file(), data);
    }
}
