package lv.id.jc.correcter

import lv.id.jc.correcter.app.DataConfig
import lv.id.jc.correcter.app.Transmitter
import lv.id.jc.correcter.coder.HammingEncoder
import spock.lang.*

import java.nio.file.Files
import java.nio.file.Path

@Title("Integration test for encoding the message")
@See("https://hyperskill.org/projects/58/stages/316/implement")
class TransmitterIntegrationSpec extends Specification {
    @TempDir
    Path temp

    @Shared
    def hexParser = HexFormat.of()

    def 'should encode message "#message"'() {
        given: 'input and output file names'
        def sourcePath = temp.resolve("source.txt")
        def targetPath = temp.resolve("target.bin")

        and: 'a message written to the source text file'
        Files.writeString(sourcePath, message)

        and: 'configurations for input and output data files'
        def source = new DataConfig(sourcePath, [])
        def target = new DataConfig(targetPath, [])

        and: 'a coder and a transmitter are under the test'
        @Subject
        def coder = new HammingEncoder()

        @Subject
        def transmitter = new Transmitter(coder, source, target)

        when: 'we run the transmitter to encode the message'
        transmitter.run()

        then: 'the transmitter creates a target binary file'
        Files.exists(targetPath)

        and: 'the target binary file is two times bigger then the source file'
        Files.size(targetPath) == 2 * Files.size(sourcePath)

        and: 'the target binary file has properly encoded message'
        Files.readAllBytes(targetPath) == encoded

        where: 'the message and the encoded message as string with hex codes'
        message | hex
        ''      | ''
        'T'     | '4A98'
        'Test'  | '4A98CC4A1E861E98'

        and: 'expected content of the target binary file defined as'
        encoded = hexParser.parseHex(hex)
    }

}
