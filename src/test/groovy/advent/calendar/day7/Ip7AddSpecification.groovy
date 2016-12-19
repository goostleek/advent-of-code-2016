package advent.calendar.day7

import spock.lang.Specification
import spock.lang.Unroll

public class Ip7AddSpecification extends Specification {

    @Unroll
    def "Should #description detect ABBA sequence in '#input'"() {
        when:
            def hasAbbaSequence = Ip7Addr.hasAbbaSequence(input)
        then:
            hasAbbaSequence == isAbbaSequence
        where:
            input    || isAbbaSequence | description
            'abba'   || true           | ''
            'aaaa'   || false          | 'not'
            'abcd'   || false          | 'not'
            'ioxxoj' || true           | ''
    }

    @Unroll
    def "Should #description detect TLS support in '#input'"() {
        given:
            def ip7Addr = new Ip7Addr(input)
        expect:
            ip7Addr.supportsTls() == supportsTls
        where:
            input                  || supportsTls | description
            'abba[mnop]qrst'       || true        | ''
            'abcd[bddb]xyyx'       || false       | 'not'
            'aaaa[qwer]tyui'       || false       | 'not'
            'ioxxoj[asdfgh]zxcvbn' || true        | ''
    }

}