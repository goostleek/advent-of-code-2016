package advent.calendar.day6

import com.google.common.base.CharMatcher
import com.google.common.base.Splitter

import spock.lang.Specification

public class SignalDecryptorSpecification extends Specification {

    def "Should crack encrypted password"() {
        given:
            def text = this.getClass().getResource('test-input').text
            def input = Splitter.on(CharMatcher.breakingWhitespace()).splitToList(text)
        expect:
            SignalDecryptor.decrypt(input) == 'easter'
    }

}