package lv.id.jc.correcter

import lv.id.jc.correcter.coder.HammingEncoder
import spock.lang.*

import java.nio.file.Files
import java.nio.file.Paths

@Title("Encode mode of transmission")
@Narrative("Integration test for Encode mode")
class EncoderTransmitterIntegrationSpec extends Specification {
    static TEMP_DIR = "src/test/resources"

    @Shared
    def source = new DataInfo("${TEMP_DIR}/source.bin", [])
    @Shared
    def target = new DataInfo("${TEMP_DIR}/target.bin", [])

    @Subject
    def coder = new HammingEncoder()

    @Subject
    def transmitter = new Transmitter(coder, source, target)

    def 'should encode the message'() {
        given: 'a message written to the source file'
        Files.writeString(sourceFile, message)

        when: 'we run the transmitter to encode the message'
        transmitter.run()

        then: 'transmitter creates an encoded binary file'
        Files.exists(targetFile)

        and: 'the encoded binary file is two times bigger then the source file'
        Files.size(targetFile) == 2 * Files.size(sourceFile)

        and: 'the target file has properly encoded message'
        Files.readAllBytes(targetFile) == encoded as byte[]

        cleanup: 'we delete the source and the target files'
        Files.delete(sourceFile)
        Files.delete(targetFile)

        where:
        message | encoded
        'T'     | [0b01001010, 0b10011000]
        'Test'  | [0x4A, 0x98, 0xCC, 0x4A, 0x1E, 0x86, 0x1E, 0x98]

        and:
        sourceFile = Paths.get(source.file())
        targetFile = Paths.get(target.file())
    }

}
