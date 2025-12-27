package org.example;

public class Day01 extends Day {

  private final CounterForIdiots counter;

  public Day01(boolean debug) {
    super("01", debug);
    this.counter = new CounterForIdiots(50);
    processLikeAnIdiot();
  }

  private void processLikeAnIdiot() {
    for (String line : input) {
      var direction = Direction.get(line);
      int rotations = Integer.parseInt(line.substring(1));
      switch (direction) {
        case LEFT -> counter.decrement(rotations);
        case RIGHT -> counter.increment(rotations);
      }
    }
  }

  @Override
  public long part1() {
    int result = counter.atZero;
    log("day 1 part 1: " + result);
    return result;
  }

  @Override
  public long part2() {
    int clicks = counter.clicks;
    log("day 1 part 2: " + clicks);
    return clicks;
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
  }

  private static class CounterForIdiots {
    private int value;
    private int atZero;
    private int clicks;

    private CounterForIdiots(int value) {
      this.value = value;
      this.atZero = 0;
      this.clicks = 0;
    }

    private void increment(int by) {
      for (int i = 0; i < by; i++) {
        value += 1;

        if (value == 100) {
          this.value = 0;
        }

        if (value == 0) {
          this.clicks++;
        }
      }
      if (value == 0) {
        this.atZero++;
      }
    }

    private void decrement(int by) {
      for (int i = 0; i < by; i++) {
        value -= 1;

        if (value == -1) {
          this.value = 99;
        }

        if (value == 0) {
          this.clicks++;
        }
      }
      if (value == 0) {
        this.atZero++;
      }
    }
  }
}
