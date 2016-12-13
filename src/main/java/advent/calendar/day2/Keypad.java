package advent.calendar.day2;

import java.util.Optional;

public class Keypad implements KeypadEvaluator {

    private KeyPressedState keyPressed = Key.K5;



    void press(Key key) {
        keyPressed = key;
    }

    Optional<Key> goTo(Direction direction) {
        return Optional.ofNullable(null);

    }

    @Override
    public Key rightKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key leftKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key topKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key bottomKey() {
        // TODO Auto-generated method stub
        return null;
    }
}
