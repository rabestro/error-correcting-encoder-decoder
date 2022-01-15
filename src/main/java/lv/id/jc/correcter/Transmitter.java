package lv.id.jc.correcter;

import lombok.Setter;
import lv.id.jc.correcter.coder.Coder;
import lv.id.jc.correcter.printer.Printer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.System.Logger.Level.WARNING;
import static java.lang.System.out;

@Setter
public class Transmitter implements Runnable {
    private static final System.Logger LOGGER = System.getLogger("Transmitter");

    private String inFile;
    private String outFile;
    private Coder coder;
    private List<Printer> inPrinters;
    private List<Printer> outPrinters;

    @Override
    public void run() {
        try {
            var data = Files.readAllBytes(Paths.get(inFile));
            out.println(inFile);
            inPrinters.forEach(printer -> out.println(printer.apply(data)));

            var coded = coder.apply(data);

            out.println();
            out.println(outFile);
            outPrinters.forEach(printer -> out.println(printer.apply(coded)));
            Files.write(Paths.get(outFile), coded);
        } catch (IOException e) {
            LOGGER.log(WARNING, e);
        }
    }
}
