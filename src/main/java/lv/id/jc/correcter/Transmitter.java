package lv.id.jc.correcter;

import lv.id.jc.correcter.coder.Coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.System.Logger.Level.WARNING;
import static java.lang.System.out;

public record Transmitter(Coder coder, DataInfo source, DataInfo target) implements Runnable {
    private static final System.Logger LOGGER = System.getLogger("Transmitter");

    @Override
    public void run() {
        try {
            var data = Files.readAllBytes(Paths.get(source.file()));
            out.println(source.file());
            source.printers().forEach(printer -> out.println(printer.apply(data)));

            var coded = coder.apply(data);

            out.println();
            out.println(target.file());
            target.printers().forEach(printer -> out.println(printer.apply(coded)));
            Files.write(Paths.get(target.file()), coded);
        } catch (IOException e) {
            LOGGER.log(WARNING, e);
        }
    }
}
