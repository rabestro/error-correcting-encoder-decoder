package lv.id.jc.correcter;

import lv.id.jc.correcter.printer.Printer;

import java.util.List;

public record DataInfo(String file, List<Printer> printers) {
}
