package advent.calendar.day3;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.io.CharStreams;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.UtilityClass;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import advent.calendar.RowsToColums;

@UtilityClass
public class TriangleDetector {

    static boolean isTriangle(@NonNull String input) {
        val edges = Splitter.onPattern("\\s+")
                .split(input.trim());
        return Collections2.orderedPermutations(edges)
            .stream()
            .allMatch(TriangleDetector::isTriangle);
    }

    private static boolean isTriangle(List<String> edges) {
        val lengths = edges.stream().mapToInt(Integer::valueOf).toArray();
        return lengths[0] + lengths[1] > lengths[2];
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = TriangleDetector.class.getResourceAsStream("input");
        List<String> puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        puzzleInput = RowsToColums.transpose(puzzleInput, Splitter.onPattern("\\s+"))
                .stream()
                .map(Joiner.on("\t")::join)
                .collect(Collectors.toList());
        val validTrianglesCount = puzzleInput.stream()
            .map(TriangleDetector::isTriangle)
            .filter(Boolean::valueOf)
            .count();
        System.out.println(validTrianglesCount);
    }

}
