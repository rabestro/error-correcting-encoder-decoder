package lv.id.jc.correcter.coder

import spock.lang.*

import java.util.random.RandomGenerator

@Issue(['19', '20'])
@Title("Bit-level error emulator")
@Narrative('''
Simulate the sending through a poor internet connection 
making one-bit errors in every byte of the text
''')
@See("https://hyperskill.org/projects/58/stages/314/implement")
class ErrorEmulatorSpec extends Specification {
    static ONE_BIT_ERRORS = [1, 2, 4, 8, 16, 32, 64, -128]

    def algorithm = Stub RandomGenerator

    void setup() {
        algorithm.nextInt(_ as int) >>> [5, 7, 2, 4, 0, 1, 3, 5, 2, 6, 7, 3]
    }

    @Subject
    def coder = new ErrorEmulator(algorithm)

    def "should make an error in one bit per byte"() {
        given: 'text as byte array'
        def data = text.bytes

        when: 'we use error emulator coder'
        def out = coder.apply(data)

        then: 'we have one-bit errors in every byte of the data'
        (0..<data.length).every { (out[it] ^ data[it]) in ONE_BIT_ERRORS }

        where:
        text << ['Test', 'Hello', 'World!', '', ' ']
    }

}
