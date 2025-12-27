package org.example;

import java.util.Optional;

public class Day03 extends Day {

  public static final int JOLT_LENGTH = 12;

  public Day03(boolean debug) {
    super("03", debug);
  }

  @Override
  public long part1() {
    // first we need the greatest value
    var processed = input.stream().map(this::joltPart1);
    long sum = processed.mapToLong(i -> i).sum();

    log("day 3 part 1: " + sum);
    return sum;
  }

  private int joltPart1(String line) {
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

  @Override
  public long part2() {
    var sum = input.stream().map(this::buildJolt).mapToLong(Long::parseLong).sum();
    log("day 3 part 2: " + sum);
    return sum;
  }

  protected String buildJolt(String line) {
    StringBuilder jolt = new StringBuilder(JOLT_LENGTH);

    for (int i = 0; i < line.length(); i++) {
      char current = line.charAt(i);
      if (current == '1' || current == '2') {
        System.out.print("");
      }

      // if jolt is empty, char goes in
      if (jolt.isEmpty()) {
        jolt.append(current);
        continue;
      }

      // if we have no more remaining to choose from, we have to use them
      int remainingCharCount = line.length() - i;
      if (remainingCharCount + jolt.length() == JOLT_LENGTH) {
        jolt.append(current);
        continue;
      }

      int maxRemovable = jolt.length() + remainingCharCount - JOLT_LENGTH;
      int removed = 0;
      // if current is bigger than the last char, we have to remove it, but
      // we cannot remove, if we are short on remaining options
      while (maxRemovable > removed && !jolt.isEmpty()) {
        char last = jolt.charAt(jolt.length() - 1);
        if (last < current) {
          jolt.deleteCharAt(jolt.length() - 1);
          removed++;
        } else {
          break;
        }
      }
      if (jolt.length() < JOLT_LENGTH) {
        jolt.append(current);
      }
    }

    return jolt.toString();
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

    @Override
    public int compareTo(Num o) {
      return this.index - o.index;
    }
  }
}
