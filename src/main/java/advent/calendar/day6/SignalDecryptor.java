package advent.calendar.day6;

import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.UtilityClass;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import advent.calendar.RowsToColums;

@UtilityClass
public class SignalDecryptor {

    public static String decrypt(List<String> parts) {
        return RowsToColums.transpose(parts, Splitter.fixedLength(1))
                .stream()
                .map(l -> l.stream().collect(groupingBy(identity(), counting()))
                    .entrySet().stream()
                    .max(comparing(Entry<String, Long>::getValue)).get())
                .map(Entry::getKey)
                .collect(Collectors.joining());
    }

    @SneakyThrows
    public static void main(String... args) {
        @Cleanup
        val puzzleInputStream = SignalDecryptor.class.getResourceAsStream("input");
        val puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        System.out.println(SignalDecryptor.decrypt(puzzleInput));
    }
}
