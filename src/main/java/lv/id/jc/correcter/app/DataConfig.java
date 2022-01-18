package lv.id.jc.correcter.app;

import lv.id.jc.correcter.printer.Printer;

import java.util.List;

public record DataConfig(String file, List<Printer> printers) {
}
