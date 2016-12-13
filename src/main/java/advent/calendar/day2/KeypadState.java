package advent.calendar.day2;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

import advent.calendar.day2.KeypadTraversal.Direction;

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

    Optional<KeypadState> goTo(Direction direction);

    static class Key1Pressed extends Key implements KeypadState {

        public Key1Pressed() {
            super("1");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case R: state = K2; break;
                case D: state = K4; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key2Pressed extends Key implements KeypadState {

        public Key2Pressed() {
            super("2");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case R: state = K3; break;
                case L: state = K1; break;
                case D: state = K5; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key3Pressed extends Key implements KeypadState {

        public Key3Pressed() {
            super("3");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case L: state = K2; break;
                case D: state = K6; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key4Pressed extends Key implements KeypadState {

        public Key4Pressed() {
            super("4");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case R: state = K5; break;
                case D: state = K7; break;
                case U: state = K1; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key5Pressed extends Key implements KeypadState {

        public Key5Pressed() {
            super("5");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case R: state = K6; break;
                case L: state = K4; break;
                case D: state = K8; break;
                case U: state = K2; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key6Pressed extends Key implements KeypadState {

        public Key6Pressed() {
            super("6");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case L: state = K5; break;
                case D: state = K9; break;
                case U: state = K3; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key7Pressed extends Key implements KeypadState {

        public Key7Pressed() {
            super("7");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case R: state = K8; break;
                case U: state = K3; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key8Pressed extends Key implements KeypadState {

        public Key8Pressed() {
            super("8");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case R: state = K9; break;
                case L: state = K7; break;
                case U: state = K5; break;
            }
            return Optional.ofNullable(state);
        }

    }

    static class Key9Pressed extends Key implements KeypadState {

        public Key9Pressed() {
            super("9");
        }

        @Override
        public Optional<KeypadState> goTo(Direction direction) {
            KeypadState state = null;
            switch (direction) {
                case L: state = K8; break;
                case U: state = K6; break;
            }
            return Optional.ofNullable(state);
        }

    }

    @Data
    static abstract class Key {
        final @NonNull String key;

        @Override
        public String toString() {
            return key;
        }
    }
}
