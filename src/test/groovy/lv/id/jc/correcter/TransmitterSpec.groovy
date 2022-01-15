package lv.id.jc.correcter

import lv.id.jc.correcter.coder.Coder
import lv.id.jc.correcter.printer.HexPrinter
import spock.lang.Specification

class TransmitterSpec extends Specification {

    def coder = Mock Coder
    def source = new DataInfo("src/test/resources/source.bin", [new HexPrinter()])
    def target = new DataInfo("src/test/resources/target.bin", [new HexPrinter()])

    Transmitter transmitter = new Transmitter(coder, source, target)

    void setup() {

    }

    void cleanup() {
    }

    def "Run"() {
        when:
        transmitter.run()

        then:
        1 * coder.apply(_) >> ([0b11001100] as byte[])
    }
}
