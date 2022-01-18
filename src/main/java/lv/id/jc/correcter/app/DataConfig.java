package lv.id.jc.correcter.app;

import lv.id.jc.correcter.printer.Printer;

import java.nio.file.Path;
import java.util.List;

public record DataConfig(Path file, List<Printer> printers) {
}
