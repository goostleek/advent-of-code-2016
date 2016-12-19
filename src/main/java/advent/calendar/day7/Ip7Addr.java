package advent.calendar.day7;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
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

import static java.util.stream.Collectors.toList;

@Data
@Builder
@RequiredArgsConstructor
public class Ip7Addr {

    private static final int ABA_SEQUENCE_LENGTH = 3;

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

    public boolean supportsSsl() {
        return supernetSequences.stream()
            .map(Ip7Addr::getAbaSequences)
            .flatMap(List::stream)
            .map(Ip7Addr::toBab)
            .anyMatch(this::hypernetSequenceContains);
    }

    private static String toBab(@NonNull String s) {
        Preconditions.checkArgument(s.length() == 3);
        return  s.substring(1) + s.charAt(1);
    }

    private boolean hypernetSequenceContains(String s) {
        return hypernetSequences.stream().anyMatch(h -> h.contains(s));
    }

    private ArrayList<String> matchSequences(final Matcher matcher) {
        val result = new ArrayList<String>(matcher.groupCount());
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    @VisibleForTesting
    static List<String> getAbaSequences(@NonNull String input) {
        return IntStream.rangeClosed(0, input.length() - ABA_SEQUENCE_LENGTH)
                .mapToObj(i -> input.substring(i, i + ABA_SEQUENCE_LENGTH))
                .filter(Ip7Addr::isAbaSequence)
                .collect(toList());
    }

    private static boolean isAbaSequence(String input) {
        return input.charAt(0) != input.charAt(1) && input.charAt(0) == input.charAt(2);
    }

    @SneakyThrows
    public static void main(String... args) {
        @Cleanup
        val puzzleInputStream = Ip7Addr.class.getResourceAsStream("input");
        val puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        val tlsSupportedIpCount = puzzleInput.stream()
            .map(Ip7Addr::new)
            .filter(Ip7Addr::supportsSsl)
            .count();
        System.out.println(tlsSupportedIpCount);
    }

}
