package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class Day01 extends Day {

  private final AtomicInteger atZero;
  private final AtomicInteger clicks;

  public Day01(boolean debug) {
    super("01", debug);
    this.atZero = new AtomicInteger(0);
    this.clicks = new AtomicInteger(0);

    processInput();
  }

  private void processInput() {
    BiFunction<Integer, String, Integer> moveDial =
        (currentPosition, input) -> {
          var direction = Direction.get(input);
          int rotations = Integer.parseInt(input.substring(1));

          clicks.getAndAdd(direction.getClicks(currentPosition, rotations));

          int relevantRotations = rotations % 100;

          int newPosition = direction.nextPosition(currentPosition, relevantRotations);

          if (newPosition == 0) {
            atZero.getAndIncrement();
          }
          return newPosition;
        };
    int current = 50;
    for (String line : input) {
      current = moveDial.apply(current, line);
    }
  }

  @Override
  public void part1() {
    log("day 1 part 1: " + atZero.get());
  }

  @Override
  public void part2() {
    log("day 1 part 2: " + clicks.get());
  }

  private enum Direction {
    LEFT,
    RIGHT;

    private static Direction get(String input) {
      if (input.startsWith("L")) {
        return LEFT;
      } else if (input.startsWith("R")) {
        return RIGHT;
      }
      throw new RuntimeException("invalid direction");
    }

    private int nextPosition(int position, int diff) {
      var newPosition =
          switch (this) {
            case LEFT -> position - diff;
            case RIGHT -> position + diff;
          };
      if (newPosition < 0) {
        return newPosition + 100;
      } else if (newPosition >= 100) {
        return newPosition - 100;
      } else {
        return newPosition;
      }
    }

    private int getClicks(int start, int rotation) {
      if (this.equals(LEFT)) {
        var end = start - rotation;
        if (end > 0) {
          return 0;
        }
        return end / -100 + 1;
      } else {
        var end = start + rotation;
        if (end < 100) {
          return 0;
        }
        return end / 100;
      }
    }
  }
}
