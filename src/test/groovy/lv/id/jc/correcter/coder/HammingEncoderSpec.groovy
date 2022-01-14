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
        'Test' | [0b01001010, 0b10011000, 0b11001100, 0b01001010, 0b00011110, 0b10000110, 0b00011110, 0b10011000]

        and:
        expected = output as byte[]
    }

}
