package lv.id.jc.correcter.printer

import spock.lang.Issue
import spock.lang.Specification

@Issue("7")
class HexPrinterSpec extends Specification {
    def "should print hex view of data"() {
        given:
        def printer = new HexPrinter()

        expect:
        printer.apply(data) == hexView

        where:
        text                             | hex
        'Test'                           | '54 65 73 74'
        'Eat more of these french buns!' | '45 61 74 20 6d 6f 72 65 20 6f 66 20 74 68 65 73 65 20 66 72 65 6e 63 68 20 62 75 6e 73 21'

        and:
        data = text as byte[]

        and:
        hexView = 'hex view: ' + hex.toUpperCase()
    }
}
