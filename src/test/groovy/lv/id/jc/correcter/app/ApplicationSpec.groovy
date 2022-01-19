package lv.id.jc.correcter.app

import spock.lang.Specification
import spock.lang.Subject

class ApplicationSpec extends Specification {
    InputStream originalIn
    PrintStream originalOut

    def out

    void setup() {
        originalIn = System.in
        originalOut = System.out

        out = new ByteArrayOutputStream()
        System.setOut new PrintStream(out)
    }

    void cleanup() {
        System.setIn originalIn
        System.setOut originalOut
    }

    def "should prompt the user for a command and execute it"() {
        setup: 'we set the system input with our content'
        System.setIn new ByteArrayInputStream(mode.bytes)

        and: 'we create an application with dummy transmissions'
        @Subject
        def app = new Application([
                encode: { println 'Encoding the message' },
                send  : { println 'Sending the message ' },
                decode: { println 'Decoding the message' }
        ])

        when: 'we run the application'
        app.run()

        and: 'we get the application output as string'
        def output = out.toString()

        then: 'the output contains a prompt for a transmission mode'
        output.contains 'Write a mode'

        and: 'the selected mode was called and it printed the result'
        output.contains transmission

        where: 'we define transmission modes and their results as'
        mode     | transmission
        'encode' | 'Encoding the message'
        'send'   | 'Sending the message'
        'decode' | 'Decoding the message'
    }
}
