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
            K1           | Direction.D || K4          | '4'
            K1           | Direction.R || K2          | '2'
            K1           | Direction.L || null        | null
            K2           | Direction.U || null        | null
            K2           | Direction.D || K5          | '5'
            K2           | Direction.R || K3          | '3'
            K2           | Direction.L || K1          | '1'
            K3           | Direction.U || null        | null
            K3           | Direction.D || K6          | '6'
            K3           | Direction.R || null        | null
            K3           | Direction.L || K2          | '2'
            K4           | Direction.U || K1          | '1'
            K4           | Direction.D || K7          | '7'
            K4           | Direction.R || K5          | '5'
            K4           | Direction.L || null        | null
            K5           | Direction.U || K2          | '2'
            K5           | Direction.D || K8          | '8'
            K5           | Direction.R || K6          | '6'
            K5           | Direction.L || K4          | '4'
            K6           | Direction.U || K3          | '3'
            K6           | Direction.D || K9          | '9'
            K6           | Direction.R || null        | null
            K6           | Direction.L || K5          | '5'
            K7           | Direction.U || K3          | '3'
            K7           | Direction.D || null        | null
            K7           | Direction.R || K8          | '8'
            K7           | Direction.L || null        | null
            K8           | Direction.U || K5          | '5'
            K8           | Direction.D || null        | null
            K8           | Direction.R || K9          | '9'
            K8           | Direction.L || K7          | '7'
            K9           | Direction.U || K6          | '6'
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
            K5           | 'ULL'   || K1
            K1           | 'RRDDD' || K9
            K9           | 'LURDL' || K8
            K8           | 'UUUUD' || K5
    }

}