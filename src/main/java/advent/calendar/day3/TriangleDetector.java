package advent.calendar.day3;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private static List<List<String>> transpose(List<String> lines) {
        val columns = new ArrayList<List<String>>();
        lines.stream().forEach(line -> transpose(line, columns));
        val transposed = new ArrayList<List<String>>();
        columns.stream().forEach(
                    column -> transposed.addAll(Lists.partition(column, columns.size()))
                );
        return transposed;
    }

    private static void transpose(String line, List<List<String>> columns) {
        val items = Splitter.onPattern("\\s+")
                .splitToList(line.trim());
        val columnCount = items.size();
        if (columns.isEmpty()) {
            IntStream.range(0, columnCount).forEach(i -> columns.add(new ArrayList<>()));
        }
        IntStream.range(0, columnCount).forEach(i -> columns.get(i).add(items.get(i)));
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = TriangleDetector.class.getResourceAsStream("input");
        List<String> puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        puzzleInput = transpose(puzzleInput)
                .stream()
                .map(l -> Joiner.on("\t").join(l))
                .collect(Collectors.toList());
        val validTrianglesCount = puzzleInput.stream()
            .map(TriangleDetector::isTriangle)
            .filter(Boolean::valueOf)
            .count();
        System.out.println(validTrianglesCount);
    }

}
