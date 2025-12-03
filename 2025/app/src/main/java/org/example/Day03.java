package org.example;

import java.util.Optional;

public class Day03 extends Day {

  public Day03(boolean debug) {
    super("03", debug);
  }

  @Override
  public void part1() {
    var sum = input.stream().map(this::jolt).mapToInt(i -> i).sum();
    log("day 3 part 1: " + sum);
  }

  @Override
  public void part2() {}

  private int jolt(String line) {
    // first we need the greatest value
    var max =
        greatest(line, 0, line.length() - 1).orElseThrow(() -> new RuntimeException("no max"));

    // then we look for the greatest in the left and then the right half
    var leftMax = greatest(line, 0, max.index - 1);
    var rightMax = greatest(line, max.index + 1, line.length() - 1);

    // we create two potential jolts: left+max vs max+right
    var left = leftMax.map(l -> Integer.parseInt(l.value + max.value)).orElse(-1);
    var right = rightMax.map(r -> Integer.parseInt(max.value + r.value)).orElse(-1);

    // return the greater value
    return Math.max(left, right);
  }

  private Optional<Num> greatest(String line, int start, int end) {
    if (end < start) {
      return Optional.empty();
    }

    var maxVal = -1;
    var maxIdx = -1;
    for (int i = start; i <= end; i++) {
      var value = Integer.parseInt(String.valueOf(line.charAt(i)));
      if (value > maxVal) {
        maxVal = value;
        maxIdx = i;
      }
    }
    return Num.from(maxVal, maxIdx);
  }

  private record Num(String value, int index) {
    private static Optional<Num> from(int v, int i) {
      return Optional.of(new Num(String.valueOf(v), i));
    }
  }
}
