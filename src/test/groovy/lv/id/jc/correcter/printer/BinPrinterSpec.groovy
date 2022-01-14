package lv.id.jc.correcter.printer

import spock.lang.Specification

class BinPrinterSpec extends Specification {
    def "should print binary view of data"() {
        given:
        def printer = new BinPrinter()

        expect:
        printer.apply(data) == binView

        where:
        data = 'Test' as byte[]

        and:
        binView = 'bin view: 01010100 01100101 01110011 01110100'
    }
}
