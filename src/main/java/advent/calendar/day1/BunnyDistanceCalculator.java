package advent.calendar.day1;

import com.google.common.base.MoreObjects;
import com.google.common.io.ByteStreams;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;


@RequiredArgsConstructor
public class BunnyDistanceCalculator {

    private enum Heading {N, E, W, S}

    private enum Direction {R, L}

    private final Set<Point> visitedPoints = new HashSet<>();
    private Point firstAlreadyVisited;

    @Value
    private static class Point {
        int x,y;
    }

    @Value
    private static class SantaMove {
        Direction direction;
        int steps;
    }

    @Value
    private static class SantaPosition {
        static SantaPosition START = new SantaPosition(Heading.N, new Point(0, 0));
        Heading heading;
        Point point;
    }

    int calculateBunnyDistance(List<String> steps) {
        Point bunnyLocation = steps.stream()
                .map(this::toMove)
                .reduce(SantaPosition.START, this::move, (a, b) -> null)
                .getPoint();
        bunnyLocation = MoreObjects.firstNonNull(firstAlreadyVisited, bunnyLocation);
        return Math.abs(bunnyLocation.x) + Math.abs(bunnyLocation.y);
    }

    private SantaMove toMove(String rawStep) {
        val direction = Direction.valueOf(String.valueOf(rawStep.charAt(0)));
        val steps = Integer.valueOf(rawStep.substring(1));
        return new SantaMove(direction, steps);
    }

    private SantaPosition move(SantaPosition start, SantaMove move) {
        final int x, y;
        final Heading heading;
        val direction = move.getDirection();
        val steps = move.getSteps();
        switch (start.heading) {
            case N: {
                heading = direction == Direction.R ? Heading.E : Heading.W;
                x = direction == Direction.R ? steps : -steps;
                y = 0;
                break;
            }
            case S: {
                heading = direction == Direction.R ? Heading.W : Heading.E;
                x = direction == Direction.R ? -steps : steps;
                y = 0;
                break;
            }
            case W: {
                heading = direction == Direction.R ? Heading.N : Heading.S;
                x = 0;
                y = direction == Direction.R ? steps : -steps;
                break;
            }
            case E: {
                heading = direction == Direction.R ? Heading.S : Heading.N;
                x = 0;
                y = direction == Direction.R ? -steps : steps;
                break;
            }
            default: { throw new IllegalArgumentException(direction.toString()); }
        }

        val newPoint = new Point(start.getPoint().getX() + x, start.getPoint().getY() + y);
        val santaPosition = new SantaPosition(heading, newPoint);
        visitPoints(start.getPoint(), santaPosition);
        return santaPosition;
    }

    private void visitPoints(Point start, SantaPosition end) {
        final int delta;
        switch (end.getHeading()) {
            case N:
            case S:
                delta = end.point.y - start.y;
                (delta < 0 ? IntStream.range(delta, 0) : IntStream.range(1, delta + 1))
                    .forEach(visitY(start));
                break;
            case W:
            case E:
                delta = end.point.x - start.x;
                (delta < 0 ? IntStream.range(delta, 0) : IntStream.range(1, delta + 1))
                    .forEach(visitX(start));
                break;
            default:  throw new IllegalArgumentException(end.getHeading().toString());
        }
    }

    private IntConsumer visitX(Point point) {
        return x -> {
            val visited = new Point(point.x + x, point.y);
            if (!visitedPoints.add(visited) && firstAlreadyVisited == null) {
                firstAlreadyVisited = visited;
            }
        };
    }

    private IntConsumer visitY(Point point) {
        return y -> {
            val visited = new Point(point.x, point.y + y);
            if (!visitedPoints.add(visited) && firstAlreadyVisited == null) {
                firstAlreadyVisited = visited;
            }
        };
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = BunnyDistanceCalculator.class.getResourceAsStream("input");
        val puzzleInput = new String(ByteStreams.toByteArray(puzzleInputStream));
        val steps = Arrays.asList(puzzleInput.split(", "));
        System.out.println(new BunnyDistanceCalculator().calculateBunnyDistance(steps));
    }
}