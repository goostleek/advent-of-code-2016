package advent.calendar.day7;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.CharStreams;

import lombok.Builder;
import lombok.Cleanup;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Data
@Builder
@RequiredArgsConstructor
public class Ip7Addr {

    private static final int ABBA_SEQUENCE_LENGTH = 4;

    private static final Pattern HYPERNET_SEQUENCES_PATTERN =
            Pattern.compile("(?!\\[)[a-z]+(?=\\])");

    private static final Pattern SUPERNET_SEQUENCES_PATTERN =
            Pattern.compile("[a-z]+(?=\\[|$)");

    private final List<String> supernetSequences;
    private final List<String> hypernetSequences;

    public Ip7Addr(@NonNull String input) {
        supernetSequences = matchSequences(SUPERNET_SEQUENCES_PATTERN.matcher(input));
        hypernetSequences = matchSequences(HYPERNET_SEQUENCES_PATTERN.matcher(input));
    }

    public boolean supportsTls() {
        return !hypernetSequences.stream().anyMatch(Ip7Addr::hasAbbaSequence)
                && supernetSequences.stream().anyMatch(Ip7Addr::hasAbbaSequence);
    }

    private ArrayList<String> matchSequences(final Matcher matcher) {
        val result = new ArrayList<String>(matcher.groupCount());
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    @VisibleForTesting
    static boolean hasAbbaSequence(@NonNull String input) {
        return IntStream.rangeClosed(0, input.length() - ABBA_SEQUENCE_LENGTH)
            .mapToObj(i -> input.substring(i, i + ABBA_SEQUENCE_LENGTH))
            .anyMatch(Ip7Addr::isAbbaSequence);
    }

    private static boolean isAbbaSequence(String input) {
        return input.charAt(0) != input.charAt(1)
                    && input.charAt(0) == input.charAt(3)
                    && input.charAt(1) == input.charAt(2);
    }

    @SneakyThrows
    public static void main(String... args) {
        @Cleanup
        val puzzleInputStream = Ip7Addr.class.getResourceAsStream("input");
        val puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        val tlsSupportedIpCount = puzzleInput.stream()
            .map(Ip7Addr::new)
            .filter(Ip7Addr::supportsTls)
            .count();
        System.out.println(tlsSupportedIpCount);
    }

}
