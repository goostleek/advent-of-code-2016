package advent.calendar.day2

import static advent.calendar.day2.KeypadState.*
import static advent.calendar.day2.KeypadTraversal.Direction

import spock.lang.Specification
import spock.lang.Unroll

public class KeypadTraversalSpecification extends Specification {

    @Unroll
    def "Should return #expectedValue starting from #initialState for #direction"() {
        given:
            def keypadTraversal = new KeypadTraversal(initialState)
        when:
            def key = keypadTraversal.goTo(direction)
        then:
            key == Optional.ofNullable(expectedKey)
            key.map({it.toString()}).orElse(null) == expectedValue
        where:
            initialState | direction   || expectedKey | expectedValue
            K1           | Direction.U || null        | null
            K1           | Direction.D || K3          | '3'
            K1           | Direction.R || null        | null
            K1           | Direction.L || null        | null
            K2           | Direction.U || null        | null
            K2           | Direction.D || K6          | '6'
            K2           | Direction.R || K3          | '3'
            K2           | Direction.L || null        | null
            K3           | Direction.U || K1          | '1'
            K3           | Direction.D || K7          | '7'
            K3           | Direction.R || K4          | '4'
            K3           | Direction.L || K2          | '2'
            K4           | Direction.U || null        | null
            K4           | Direction.D || K8          | '8'
            K4           | Direction.R || null        | null
            K4           | Direction.L || K3          | '3'
            K5           | Direction.U || null        | null
            K5           | Direction.D || null        | null
            K5           | Direction.R || K6          | '6'
            K5           | Direction.L || null        | null
            K6           | Direction.U || K2          | '2'
            K6           | Direction.D || KA          | 'A'
            K6           | Direction.R || K7          | '7'
            K6           | Direction.L || K5          | '5'
            K7           | Direction.U || K3          | '3'
            K7           | Direction.D || KB          | 'B'
            K7           | Direction.R || K8          | '8'
            K7           | Direction.L || K6          | '6'
            K8           | Direction.U || K4          | '4'
            K8           | Direction.D || KC          | 'C'
            K8           | Direction.R || K9          | '9'
            K8           | Direction.L || K7          | '7'
            K9           | Direction.U || null        | null
            K9           | Direction.D || null        | null
            K9           | Direction.R || null        | null
            K9           | Direction.L || K8          | '8'
    }

    @Unroll
    def "Should return #expectedOutput starting from #initialState for #input"() {
        given:
            def keypadTraversal = new KeypadTraversal(initialState)
        when:
            def traversedKey = keypadTraversal.traverse(input)
        then:
            traversedKey == expectedOutput
        where:
            initialState | input   || expectedOutput
            K5           | 'ULL'   || K5
            K5           | 'RRDDD' || KD
            KD           | 'LURDL' || KB
            KB           | 'UUUUD' || K3
    }

}