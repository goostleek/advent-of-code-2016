package advent.calendar.day2;

import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
class KeypadTraversal {

    static enum Direction {
        U, D, L, R
    }

    @Setter(AccessLevel.PRIVATE)
    private KeypadState state;

    Optional<KeypadState> goTo(Direction direction) {
        val nextState = state.goTo(direction);
        nextState.ifPresent(this::setState);
        return nextState;
    }

    KeypadState traverse(@NonNull String input) {
        Splitter.fixedLength(1).splitToList(input)
                .stream()
                .map(Direction::valueOf)
                .forEach(this::goTo);
        return state;
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = KeypadTraversal.class.getResourceAsStream("input");
        val puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        val keypadTraversal = new KeypadTraversal(KeypadState.K5);
        val sequence = puzzleInput.stream()
            .map(keypadTraversal::traverse)
            .map(KeypadState::toString)
            .collect(Collectors.joining());
        System.out.println(sequence);
    }

}
