package lv.id.jc.correcter.coder

import spock.lang.Rollup
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Hamming error-correction decoder")
class HammingDecoderSpec extends Specification {
    @Subject
    def coder = new HammingDecoder()

    @Rollup
    def 'should decode data by Hamming algorithm'() {
        given:
        def data = received as byte[]

        when:
        def decoded = coder.apply(data)

        then:
        decoded == text as byte[]

        where:
        received                                                                                         | text
        [0b01001010, 0b10011000]                                                                         | 'T'
        [0b00001010, 0b10010000]                                                                         | 'T'
        [0b11001010, 0b00011000]                                                                         | 'T'
        [0b00001010, 0b10011000]                                                                         | 'T'
        [0b11001010, 0b10011000]                                                                         | 'T'
        [0b01001010, 0b10011000, 0b11001100, 0b01001010, 0b00011110, 0b10000110, 0b00011110, 0b10011000] | 'Test'
        [0b01101010, 0b00011000, 0b11101100, 0b00001010, 0b00111111, 0b10001111, 0b00111111, 0b10001000] | 'Test'

    }

}
