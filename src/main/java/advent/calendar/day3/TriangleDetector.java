package advent.calendar.day3;

import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.io.CharStreams;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class TriangleDetector {

    boolean isTriangle(@NonNull String input) {
        val edges = Splitter.onPattern("\\s+")
                .split(input.trim());
        return Collections2.orderedPermutations(edges)
            .stream()
            .allMatch(this::isTriangle);
    }

    private boolean isTriangle(List<String> edges) {
        val lengths = edges.stream().mapToInt(Integer::valueOf).toArray();
        return lengths[0] + lengths[1] > lengths[2];
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = TriangleDetector.class.getResourceAsStream("input");
        final List<String> puzzleInput = CharStreams.readLines(new InputStreamReader(puzzleInputStream, Charset.forName("UTF-8")));
        val traingleDetector = new TriangleDetector();
        val validTrianglesCount = puzzleInput.stream()
            .map(traingleDetector::isTriangle)
            .filter(Boolean::valueOf)
            .count();
        System.out.println(validTrianglesCount);
    }

}
