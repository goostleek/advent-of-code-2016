package advent.calendar;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RowsToColums {

    public static List<List<String>> transpose(List<String> lines, Splitter columnSplitter) {
        return transpose(lines, columnSplitter, -1);
    }

    private static List<List<String>> transpose(List<String> lines, Splitter columnSplitter, int desiredColumnHeight) {
        val columns = new ArrayList<List<String>>();
        lines.stream().forEach(line -> transpose(line, columns, columnSplitter));
        val transposed = new ArrayList<List<String>>();
        val columnHeight = desiredColumnHeight < 0 ? Integer.MAX_VALUE : desiredColumnHeight;
        columns.stream().forEach(
                    column -> transposed.addAll(Lists.partition(column, columnHeight))
                );
        return transposed;
    }

    private static void transpose(String line, List<List<String>> columns, Splitter columnSplitter) {
        val items = columnSplitter.splitToList(line.trim());
        val columnCount = items.size();
        if (columns.isEmpty()) {
            IntStream.range(0, columnCount).forEach(i -> columns.add(new ArrayList<>()));
        }
        IntStream.range(0, columnCount).forEach(i -> columns.get(i).add(items.get(i)));
    }
}
