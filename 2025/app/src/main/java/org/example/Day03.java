package org.example;

import java.util.Optional;

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
    var first = input.stream().map(this::cut).toList();

    for (String line : first) {
      if (needsWork(line)) {
        System.out.println(line);
      }
    }

    var some = input.stream().map(this::cut).map(this::easyRemove).toList();
    var another = some.stream().map(this::finishUp).toList();
    long sum = another.stream().map(Long::parseLong).mapToLong(i -> i).sum();
    log("day 3 part 2: " + sum);
    // 171012734629079 too low
    // 172562798287789 too low
    // 172584523753321 too low
  }

  private boolean needsWork(String line) {
    var greatest = greatest(line, 0, line.length() - 1).get().value;
    var first = line.substring(0, 1);
    return !greatest.equals(first);
  }

  private String finishUp(StringAndMin stringAndMin) {
    var number = stringAndMin.s;
    if (stringAndMin.s.length() == 12) {
      return stringAndMin.s;
    }

    //    for (int valueToRemove = stringAndMin.min; valueToRemove <= 9; valueToRemove++) {
    while (number.length() > 12) {
      var smallest = smallest(number);
      // remove the smallest and change number
      // 01234567
      // 9...s...
      var left = number.substring(0, smallest.index);
      var right = number.substring(smallest.index + 1);
      number = left + right;
    }
    //    }

    return number;
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

  // find the greatest that has at least 11 te the right
  public String cut(String line) {
    var index =
        greatest(line, 0, line.length() - 12)
            .map(Num::index)
            .orElseThrow(() -> new RuntimeException("no max"));
    return line.substring(index);
  }

  public StringAndMin easyRemove(String line) {
    var greatest = greatest(line, 0, line.length() - 1);
    var lastIndex = findLastHighIndex(line, greatest.get().getValue());

    int i;
    for (i = 0; i < 9; i++) {
      var without = line.replace(String.valueOf(i), "");
      if (without.length() < 12) {
        return new StringAndMin(line, i);
      }
      if (without.length() == 12) {
        return new StringAndMin(line, i);
      }
      line = without;
    }
    return new StringAndMin(line, i);
  }

  // find the greatest number from the left that has at least 11 neighbors to the right
  // loop:
  // set value high to the greatest and get all the indexes where they are
  // remove number from greatest towards the last high number
  // after removal if remaining's length is 12, return it
  // once there is nothing to remove, high--, then loop again
  public long fancyJolt(String line) {
    // find the greatest that has at least 11 te the right
    Num max =
        greatest(line, 0, line.length() - 12).orElseThrow(() -> new RuntimeException("no max"));
    String remaining = line.substring(max.index);

    if (remaining.length() == 12) {
      return Long.parseLong(remaining);
    }

    for (int high = max.getValue(); high >= 0; high--) {
      remaining = shortenBetweenStartAndLastHigh(remaining, high);
      if (remaining.length() == 12) {
        break;
      }
    }

    if (remaining.length() < 12) {
      throw new RuntimeException("remaining is too short");
    }

    return Long.parseLong(remaining.substring(0, 12));
  }

  private String shortenBetweenStartAndLastHigh(String remaining, int high) {
    var lastHighIndex = findLastHighIndex(remaining, high);
    if (lastHighIndex == -1) {
      return remaining;
    }

    for (int valueToRemove = 1;
        remaining.length() > 12 && (valueToRemove < high || high == 1);
        valueToRemove++) {
      while (remaining.length() > 12) {
        var indexToRemove = findIndexToRemove(remaining, valueToRemove, lastHighIndex);
        if (indexToRemove == -1) {
          break;
        }
        remaining = removeIndex(remaining, indexToRemove);
        lastHighIndex--;
      }
    }
    return remaining;
  }

  private String removeIndex(String line, int indexToRemove) {
    var left = line.substring(0, indexToRemove);
    var right = line.substring(indexToRemove + 1);
    return left + right;
  }

  private int findIndexToRemove(String line, int valueToRemove, int lastIndex) {
    for (int i = 1; i < lastIndex; i++) {
      if (Integer.parseInt(String.valueOf(line.charAt(i))) == valueToRemove) {
        return i;
      }
    }
    return -1;
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

  private int findLastHighIndex(String line, int value) {
    var index = -1;
    for (int i = 1; i < line.length(); i++) {
      var stringValue = String.valueOf(line.charAt(i));
      if (Integer.parseInt(stringValue) == value) {
        index = i;
      }
    }
    return index;
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

  private record Num(String value, int index) implements Comparable<Num> {
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

  private record StringAndMin(String s, int min) {}
}
