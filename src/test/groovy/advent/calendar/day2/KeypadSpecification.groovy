package advent.calendar.day2

import static advent.calendar.day2.Keypad.Direction
import static advent.calendar.day2.Keypad.Key

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

public class KeypadSpecification extends Specification {

    @Subject
    Keypad keypad = new Keypad()

    @Unroll
    def "Should return #expectedKey if #keyPressed and next direction is #direction"() {
        given:
            keypad.press(keyPressed)
        when:
            def nextKey = keyPad.keyAt(direction)
        then:
            expectedKey == nextKey.orElse(null)
        where:
            keyPressed | direction   || expectedKey
            Key.K1     | Direction.U || null
            Key.K1     | Direction.D || Key.K4
            Key.K1     | Direction.R || null
            Key.K1     | Direction.L || Key.K2
            Key.K2     | Direction.U || null
            Key.K2     | Direction.D || Key.K5
            Key.K2     | Direction.R || Key.K3
            Key.K2     | Direction.L || Key.K1
            Key.K3     | Direction.U || null
            Key.K3     | Direction.D || Key.K6
            Key.K3     | Direction.R || null
            Key.K3     | Direction.L || Key.K2
            Key.K4     | Direction.U || Key.K1
            Key.K4     | Direction.D || Key.K7
            Key.K4     | Direction.R || Key.K5
            Key.K4     | Direction.L || null
            Key.K5     | Direction.U || Key.K2
            Key.K5     | Direction.D || Key.K8
            Key.K5     | Direction.R || Key.K6
            Key.K5     | Direction.L || Key.K4
            Key.K6     | Direction.U || Key.K3
            Key.K6     | Direction.D || Key.K9
            Key.K6     | Direction.R || null
            Key.K6     | Direction.L || Key.K5
            Key.K7     | Direction.U || Key.K4
            Key.K7     | Direction.D || null
            Key.K7     | Direction.R || Key.K8
            Key.K7     | Direction.L || null
            Key.K8     | Direction.U || Key.K5
            Key.K8     | Direction.D || null
            Key.K8     | Direction.R || Key.K9
            Key.K8     | Direction.L || Key.K7
            Key.K9     | Direction.U || Key.K6
            Key.K9     | Direction.D || null
            Key.K9     | Direction.R || null
            Key.K9     | Direction.L || Key.K8
    }
}