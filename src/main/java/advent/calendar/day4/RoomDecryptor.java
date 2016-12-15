package advent.calendar.day4;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;

import lombok.Builder;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import lombok.experimental.UtilityClass;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

@UtilityClass
public class RoomDecryptor {

    @Value@Builder
    public static class DecryptionResult {
        private int sectorId;
        private boolean isValid;
        private String name;
    }

    /**
     *  https://regex101.com/r/esX4Pj/4
     */
    private static final Pattern PARSER_PATTERN
        = Pattern.compile("(?<encryptedName>[^\\d]*)(?<sectorId>(?!-)\\d+(?=\\[))\\[(?<checksum>.+)\\]");

    public static DecryptionResult decrypt(@NonNull String encryptedInput) {
        val matcher = PARSER_PATTERN.matcher(encryptedInput);

        Preconditions.checkArgument(matcher.find() && matcher.groupCount() == 3,
                encryptedInput + " is not a valid room encryption");

        val sectorId = Integer.valueOf(matcher.group("sectorId"));
        val encryptedName = matcher.group("encryptedName");
        return DecryptionResult.builder()
            .sectorId(sectorId)
            .isValid(verifyChecksum(encryptedName, matcher.group("checksum")))
            .name(decryptName(encryptedName, sectorId))
            .build();
    }

    private static String decryptName(String encryptedName, int sectorId) {
        return encryptedName.chars()
            .mapToObj(i -> (char)i)
            .map(a -> rotate(a , sectorId))
            .map(String::valueOf)
            .collect(joining())
            .replaceAll(String.valueOf(rotate('-', sectorId)), " ");
    }

    static char rotate(char c, int distance) {
        val aToZlettersCount = 26;
        return (char) ((c + distance - 'a') % aToZlettersCount + 'a');
    }

    private static boolean verifyChecksum(String encryptedName, String expectedChecksum) {
        return encryptedName.chars()
            .mapToObj(i -> (char)i)
            .filter(CharMatcher.javaLetter()::matches)
            .collect(groupingBy(Function.identity(), counting()))
            .entrySet().stream()
            .sorted(compareByOccurenceThenAlphabetically())
            .limit(expectedChecksum.length())
            .collect(toMap(Entry::getKey, Entry::getValue, (a, b) -> null, LinkedHashMap::new))
            .keySet().stream()
            .map(String::valueOf).collect(joining())
            .equals(expectedChecksum);
    }

    private static Comparator<Entry<Character, Long>> compareByOccurenceThenAlphabetically() {
        return Comparator.comparing((Entry<Character, Long> o) -> o.getValue()).reversed()
                .thenComparing(o -> o.getKey());
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = RoomDecryptor.class.getResourceAsStream("input");
        val puzzleInput = CharStreams.readLines(
                new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        puzzleInput.stream()
            .map(RoomDecryptor::decrypt)
            .filter(DecryptionResult::isValid)
            .filter(a -> a.getName().contains("north"))
            .map(DecryptionResult::getSectorId)
            .forEach(System.out::println);
    }
}
