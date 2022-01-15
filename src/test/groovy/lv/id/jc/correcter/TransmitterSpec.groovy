package lv.id.jc.correcter

import lv.id.jc.correcter.coder.Coder
import lv.id.jc.correcter.printer.HexPrinter
import spock.lang.Specification

class TransmitterSpec extends Specification {

    def coder = Mock Coder
    def source = new DataInfo("src/test/resources/source.bin", [new HexPrinter()])
    def target = new DataInfo("src/test/resources/target.bin", [new HexPrinter()])

    void setup() {

    }

    void cleanup() {
    }

    def "Run"() {
        given:
        def transmitter = new Transmitter(coder, source, target)

        when:
        transmitter.run()

        then:
        1 * coder.apply(_) >> ([0b11001100] as byte[])
    }

    def 'should skip coder call if no file found'() {
        given:
        def wrongSource = new DataInfo("src/test/resources/no-file.bin", [])
        def transmitter = new Transmitter(coder, wrongSource, target)

        when:
        transmitter.run()

        then:
        0 * coder.apply(_)
    }


}
