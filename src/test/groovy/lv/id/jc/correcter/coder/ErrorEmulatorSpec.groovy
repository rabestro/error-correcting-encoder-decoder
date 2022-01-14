package lv.id.jc.correcter.coder

import spock.lang.Specification
import spock.lang.Subject

class ErrorEmulatorSpec extends Specification {
    static ONE_BIT_ERRORS = [1, 2, 4, 8, 16, 32, 64, -128]

    @Subject
    def coder = new ErrorEmulator()

    def "should make an error in one bit per byte"() {
        given:
        def data = text.bytes

        when:
        def out = coder.apply(data)

        then:
        (0..<data.length).every { (out[it] ^ data[it]) in ONE_BIT_ERRORS }

        where:
        text << ['Test', 'Hello', 'World!', '', ' ']
    }

}
