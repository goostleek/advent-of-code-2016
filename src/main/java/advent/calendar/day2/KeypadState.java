package advent.calendar.day2;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
interface KeypadState {

    static final KeypadState K1 = new Key1Pressed();
    static final KeypadState K2 = new Key2Pressed();
    static final KeypadState K3 = new Key3Pressed();
    static final KeypadState K4 = new Key4Pressed();
    static final KeypadState K5 = new Key5Pressed();
    static final KeypadState K6 = new Key6Pressed();
    static final KeypadState K7 = new Key7Pressed();
    static final KeypadState K8 = new Key8Pressed();
    static final KeypadState K9 = new Key9Pressed();

    static enum Direction {
        U, D, L, R
    }

    Optional<KeypadState> goTo(Direction direction);
    int toDigit();

    public static class Key1Pressed implements KeypadState {

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            switch (direction) {
                case R: return of(K1);
                case D: return of(K4);
                default: return empty();
            }
        }

        @Override
        public int toDigit() {return 1;}

    }

    public static class Key2Pressed implements KeypadState {

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            switch (direction) {
                case R: return of(K3);
                case L: return of(K1);
                case D: return of(K5);
                default: return empty();
            }
        }

        @Override
        public int toDigit() {return 2;}
    }

    public static class Key3Pressed implements KeypadState {

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            switch (direction) {
                case L: return of(K2);
                case D: return of(K6);
                default: return empty();
            }
        }

        @Override
        public int toDigit() {return 3;}

    }

    public static class Key4Pressed implements KeypadState {

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            switch (direction) {
                case R: return of(K5);
                case D: return of(K7);
                case U: return of(K1);
                default: return empty();
            }
        }

        @Override
        public int toDigit() {return 4;}
    }

    public static class Key5Pressed implements KeypadState {
        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            switch (direction) {
                case R: return of(K6);
                case L: return of(K4);
                case D: return of(K8);
                case U: return of(K2);
                default: return empty();
            }
        }
    }

}
