package lv.id.jc.correcter.coder

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Hamming error-correction encoder")
class HammingEncoderSpec extends Specification {
    @Subject
    def coder = new HammingEncoder()

    def 'should encode data by Hamming algorithm'() {
        given:
        def data = text.bytes

        expect:
        coder.apply(data) == expected

        where:
        text   | output
        'T'    | [0b01001010, 0b10011000]
        'Test' | [0x4A, 0x98, 0xCC, 0x4A, 0x1E, 0x86, 0x1E, 0x98]

        and:
        expected = output as byte[]
    }

}
