package lv.id.jc.correcter.app;

import org.springframework.format.Printer;

import java.nio.file.Path;
import java.util.List;

public record DataConfig(Path file, List<Printer<byte[]>> printers) {
}
