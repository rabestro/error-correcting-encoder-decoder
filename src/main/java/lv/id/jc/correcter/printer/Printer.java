package lv.id.jc.correcter.printer;

import java.util.function.Function;

/**
 * Represents a printer for binary data
 */
public interface Printer extends Function<byte[], String> {
}
