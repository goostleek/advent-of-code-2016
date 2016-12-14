package advent.calendar.day3

import static advent.calendar.day2.KeypadState.*

import spock.lang.Specification
import spock.lang.Unroll

public class TriangleDetectorSpecification extends Specification {

    @Unroll
    def "Should return #expectedOutput for #input"() {
        when:
            def isTriangle = TriangleDetector.isTriangle(input)
        then:
            isTriangle == expectedOutput
        where:
            input     || expectedOutput
            '5 10 25' || false
            '2 3 4'   || true
    }

}