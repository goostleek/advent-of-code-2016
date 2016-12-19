package advent.calendar.day7

import spock.lang.Specification
import spock.lang.Unroll

public class Ip7AddSpecification extends Specification {

    @Unroll
    def "Should #description detect ABA sequence in '#input'"() {
        when:
            def abaSequences = Ip7Addr.getAbaSequences(input)
        then:
            !abaSequences.empty == isAbaSequence
        where:
            input   || isAbaSequence  | description
            'aba'   || true           | ''
            'aaa'   || false          | 'not'
            'aaba'  || true           | ''
            'abaa'  || true           | ''
            'ioxoj' || true           | ''
    }

    @Unroll
    def "Should #description detect SSL support in '#input'"() {
        given:
            def ip7Addr = new Ip7Addr(input)
        expect:
            ip7Addr.supportsSsl() == supportsSsl
        where:
            input           || supportsSsl | description
            'zaba[baba]xyz' || true        | ''
            'xyx[xyx]xyx'   || false       | 'not'
            'aaa[kek]eke'   || true        | ''
            'zazbz[bzb]cdb' || true        | ''
    }

}