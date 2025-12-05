package org.example;

import java.util.Optional;

public class Day03 extends Day {

  public Day03(boolean debug) {
    super("03", debug);
  }

  @Override
  public void part1() {
    var processed =
        input.stream()
            .map(
                line -> {
                  // first we need the greatest value
                  var max =
                      greatest(line, 0, line.length() - 1)
                          .orElseThrow(() -> new RuntimeException("no max"));

                  // then we look for the greatest in the left and then the right half
                  var leftMax = greatest(line, 0, max.index - 1);
                  var rightMax = greatest(line, max.index + 1, line.length() - 1);

                  // we create two potential jolts: left+max vs max+right
                  var left = leftMax.map(l -> Integer.parseInt(l.value + max.value)).orElse(-1);
                  var right = rightMax.map(r -> Integer.parseInt(max.value + r.value)).orElse(-1);

                  // return the greater value
                  return Math.max(left, right);
                });
    long sum = processed.mapToLong(i -> i).sum();

    log("day 3 part 1: " + sum);
  }

  @Override
  public void part2() {
    var jolts = input.stream().map(this::joltForPart2).toList();

    var sum = jolts.stream().mapToLong(Long::parseLong).sum();
    log("day 3 part 2: " + sum);
    // 171012734629079 too low
    // 172562798287789 too low
    // 172584523753321 too low
    // 172519679681899
    // 169697055064753
    // 171040377797011
  }

  protected String joltForPart2(String line) {
    // this must be the starting number
    Num firstNum =
        greatest(line, 0, line.length() - 12)
            .orElseThrow(() -> new RuntimeException("no first num"));

    var remaining = line.substring(firstNum.index);

    Num greatestOfLast =
        greatest(remaining, remaining.length() - 11, remaining.length() - 1).get(); // guaranteed

    int removeUntilIndex;
    if (greatestOfLast.getValue() > firstNum.getValue()) {
      removeUntilIndex = greatestOfLast.index;
    } else {
      removeUntilIndex = remaining.length();
    }

    while (remaining.length() > 12) {
      remaining = removeSmallestUntil(remaining, removeUntilIndex);
      removeUntilIndex--;
    }

    return remaining;
  }

  private String removeIndex(String line, int indexToRemove) {
    var left = line.substring(0, indexToRemove);
    var right = line.substring(indexToRemove + 1);
    return left + right;
  }

  protected Optional<Num> greatest(String line, int start, int end) {
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

  protected record Num(String value, int index) implements Comparable<Num> {

    private static Optional<Num> from(int v, int i) {
      return Optional.of(new Num(String.valueOf(v), i));
    }

    private int getValue() {
      return Integer.parseInt(value);
    }

    @Override
    public int compareTo(Num o) {
      return this.index - o.index;
    }
  }

  private String removeSmallestUntil(String line, int index) {
    var minVal = 10;
    var minIdx = -1;
    for (int i = index - 1; i >= 0; i--) {
      var value = Integer.parseInt(String.valueOf(line.charAt(i)));
      if (value <= minVal) {
        minVal = value;
        minIdx = i;
      }
    }
    return removeIndex(line, minIdx);
  }
}
