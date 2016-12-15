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
    }

    /**
     *  https://regex101.com/r/esX4Pj/4
     */
    private static final Pattern PARSER_PATTERN
        = Pattern.compile("(?<name>[^\\d]*)(?<sectorId>(?!-)\\d+(?=\\[))\\[(?<checksum>.+)\\]");

    public static DecryptionResult decrypt(@NonNull String encryptedInput) {
        val matcher = PARSER_PATTERN.matcher(encryptedInput);
        Preconditions.checkArgument(matcher.find() && matcher.groupCount() == 3,
                encryptedInput + " is not a valid room encryption");
        return DecryptionResult.builder()
            .sectorId(Integer.valueOf(matcher.group("sectorId")))
            .isValid(verifyChecksum(matcher.group("name"), matcher.group("checksum")))
            .build();
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
        val sectorIdsSum = puzzleInput.stream()
            .map(RoomDecryptor::decrypt)
            .filter(DecryptionResult::isValid)
            .mapToInt(DecryptionResult::getSectorId)
            .sum();
        System.out.println(sectorIdsSum);
    }
}
