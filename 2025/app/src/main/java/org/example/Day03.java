package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day03 extends Day {

  public Day03(boolean debug) {
    super("03", debug);
  }

  @Override
  public void part1() {
    int sum = input.stream().map(this::jolt).mapToInt(i -> i).sum();
    log("day 3 part 1: " + sum);
  }

  @Override
  public void part2() {
    long sum = input.stream().map(this::jolt12WithRemoval).mapToLong(i -> i).sum();
    log("day 3 part 2: " + sum);
    // 171012734629079 too low
    // 171012734629079
  }

  public long jolt12(String line) {
    // find the greatest that has at least 11 te the right
    var max =
        greatest(line, 0, line.length() - 12).orElseThrow(() -> new RuntimeException("no max"));

    // get the 11 highest from the right side
    var numbers = new ArrayList<Num>(12);
    numbers.add(max);
    for (var i = 0; i < 11; i++) {
      var num =
          greatestExcept(
              line,
              max.index + 1,
              line.length() - 1,
              numbers.stream().map(Num::index).collect(Collectors.toSet()));
      numbers.add(num);
    }
    var jolt =
        numbers.stream()
            .sorted((Comparator.comparingInt(Num::index)))
            .map(Num::value)
            .collect(Collectors.joining());
    return Long.parseLong(jolt);
  }

  public long jolt12WithRemoval(String line) {
    // find the greatest that has at least 11 te the right
    var max =
        greatest(line, 0, line.length() - 12).orElseThrow(() -> new RuntimeException("no max"));

    // remove the smallest number, starting from the left side until we have 12 numbers
    var number = line.substring(max.index);
    while (number.length() > 12) {
      var smallest = smallest(number);
      // remove the smallest and change number
      // 01234567
      // 9...s...
      var left = number.substring(0, smallest.index);
      var right = number.substring(smallest.index + 1);
      number = left + right;
    }

    return Long.parseLong(number);
  }

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

  private Num smallest(String line) {
    var minVal = 10;
    var minIdx = -1;
    for (int i = 0; i < line.length(); i++) {
      var value = Integer.parseInt(String.valueOf(line.charAt(i)));
      if (value < minVal) {
        minVal = value;
        minIdx = i;
      }
    }
    return new Num(String.valueOf(minVal), minIdx);
  }

  private Num greatestExcept(String line, int start, int end, Set<Integer> exceptIdx) {
    // this case we guarantee that start is lower than end
    var maxVal = -1;
    var maxIdx = -1;
    for (int i = end; i >= start; i--) {
      if (exceptIdx.contains(i)) {
        continue;
      }
      var value = Integer.parseInt(String.valueOf(line.charAt(i)));
      if (value > maxVal) {
        maxVal = value;
        maxIdx = i;
      }
    }
    return new Num(String.valueOf(maxVal), maxIdx);
  }

  private record Num(String value, int index) implements Comparable<Num> {
    private static Optional<Num> from(int v, int i) {
      return Optional.of(new Num(String.valueOf(v), i));
    }

    @Override
    public int compareTo(Num o) {
      return this.index - o.index;
    }
  }
}
