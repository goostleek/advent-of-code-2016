package advent.calendar.day5

import static advent.calendar.day2.KeypadState.*

import spock.lang.Specification

public class PasswordDecryptorSpecification extends Specification {

    def "Should crack encrypted password"() {
        expect:
            PasswordDecryptor.decrypt('abc') == '18f47a30'
    }

}