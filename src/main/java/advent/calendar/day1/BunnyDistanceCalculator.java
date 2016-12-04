package advent.calendar.day1;

import com.google.common.io.ByteStreams;

import lombok.Cleanup;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class BunnyDistanceCalculator {

    private final List<String> steps;

    private enum Heading {N, E, W, S}

    private enum Direction {R, L}

    @Value
    private static class SantaMove {
        Direction direction;
        int steps;
    }

    @Value
    @EqualsAndHashCode
    private static class SantaPosition {
        static SantaPosition START = new SantaPosition(Heading.N, new Point(0, 0));
        Heading heading;
        Point point;
    }

    int calculateBunnyDistance() {
        val bunnyLocation = steps.stream()
                .map(this::toMove)
                .reduce(SantaPosition.START, this::move, (a, b) -> null)
                .getPoint();
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

        val newPoint = new Point(start.getPoint());
        newPoint.translate(x, y);
        return new SantaPosition(heading, newPoint);
    }

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        val puzzleInputStream = BunnyDistanceCalculator.class.getResourceAsStream("input");
        val puzzleInput = new String(ByteStreams.toByteArray(puzzleInputStream));
        val steps = Arrays.asList(puzzleInput.split(", "));
        System.out.println(new BunnyDistanceCalculator(steps).calculateBunnyDistance());
    }
}