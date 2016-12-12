package advent.calendar.day1

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

public class BunnyDistanceCalculatorSpecification extends Specification {

    @Subject
    BunnyDistanceCalculator subject = new BunnyDistanceCalculator()

    @Unroll
    def "Should calculate distance for #steps"() {
        when:
            def calculatedDistance = subject.calculateBunnyDistance(steps)
        then:
            calculatedDistance == expectedDistance
        where:
            steps        || expectedDistance
            ['R2', 'L3'] || 5
            ['R2', 'R2', 'R2'] || 2
            ['R2', 'R2', 'R2', 'R2'] || 0
            ['L2', 'L2', 'L2', 'L2'] || 0
            ['R5', 'L5', 'R5', 'R3'] || 12
            ['R2', 'R1', 'R1', 'R2'] || 1
            ['R8', 'R4', 'R4', 'R100'] || 4
            ['L2', 'L1', 'L1', 'L100'] || 1
    }
}