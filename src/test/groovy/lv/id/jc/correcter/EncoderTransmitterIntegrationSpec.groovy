package lv.id.jc.correcter

import lv.id.jc.correcter.app.DataConfig
import lv.id.jc.correcter.app.Transmitter
import lv.id.jc.correcter.coder.HammingEncoder
import spock.lang.*

import java.nio.file.Files
import java.nio.file.Path

@Title("Integration test for encoding the message")
@See("https://hyperskill.org/projects/58/stages/316/implement")
class EncoderTransmitterIntegrationSpec extends Specification {
    @TempDir
    Path temp

    @Shared
    def hexParser = HexFormat.of()

    def 'should encode message "#message"'() {
        given: 'input and output file names'
        def sourcePath = temp.resolve("source.bin")
        def targetPath = temp.resolve("target.bin")

        and: 'a message written to the source file'
        Files.writeString(sourcePath, message)

        and: 'configurations for input and output data files'
        def source = new DataConfig(sourcePath, [])
        def target = new DataConfig(targetPath, [])

        and: 'coder and transmitter are under the test'
        @Subject
        def coder = new HammingEncoder()

        @Subject
        def transmitter = new Transmitter(coder, source, target)

        when: 'we run the transmitter to encode the message'
        transmitter.run()

        then: 'transmitter creates a binary file'
        Files.exists(targetPath)

        and: 'the binary file is two times bigger then the source file'
        Files.size(targetPath) == 2 * Files.size(sourcePath)

        and: 'the target file has properly encoded message'
        Files.readAllBytes(targetPath) == encoded

        where: 'source message and encoded message in hex view'
        message | hex
        ''      | ''
        'T'     | '4A98'
        'Test'  | '4A98CC4A1E861E98'

        and: 'expected encoded content of the target file'
        encoded = hexParser.parseHex(hex)
    }

}
