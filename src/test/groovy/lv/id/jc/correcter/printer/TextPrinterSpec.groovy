package lv.id.jc.correcter.printer

import spock.lang.Rollup
import spock.lang.Specification
import spock.lang.Subject

class TextPrinterSpec extends Specification {

    @Rollup
    def "should print text view of data"() {
        given:
        @Subject
        def printer = new TextPrinter()

        and:
        def data = text.bytes

        expect:
        printer.print(data, Locale.ENGLISH) == expected

        where:
        text << ['Test', 'Eat more of these french buns!', '']

        and:
        expected = "text view: $text"
    }
}
