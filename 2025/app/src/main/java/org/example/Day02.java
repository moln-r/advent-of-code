package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 extends Day {

  private final List<Range> ranges;

  public Day02(boolean debug) {
    super("02", debug);

    this.ranges =
        Arrays.stream(this.input.getFirst().split(","))
            .map(range -> range.split("-"))
            .map(Range::new)
            .toList();
  }

  @Override
  public void part1() {
    long sum = this.ranges.stream().map(Range::invalidSumPart1).mapToLong(Long::longValue).sum();
    log("day 2 part 1: " + sum);
  }

  @Override
  public void part2() {
    long sum = this.ranges.stream().map(Range::invalidSumPart2).mapToLong(Long::longValue).sum();
    log("day 2 part 2: " + sum);
  }

  private static class Range {
    private final long x;
    private final long y;
    private final List<Long> part1InvalidIds;
    private final List<Long> part2InvalidIds;

    public Range(String[] range) {
      this.x = Long.parseLong(range[0]);
      this.y = Long.parseLong(range[1]);

      this.part1InvalidIds = new ArrayList<>();
      this.part2InvalidIds = new ArrayList<>();
      getInvalidIds();
    }

    private void getInvalidIds() {
      for (long i = x; i <= y; i++) {
        if (isMirrored(i)) {
          part1InvalidIds.add(i);
        }
        if (isSequence(i)) {
          part2InvalidIds.add(i);
        }
      }
    }

    private boolean isMirrored(long id) {
      var stringId = String.valueOf(id);
      if (stringId.length() % 2 != 0) {
        return false;
      }
      var firstHalf = stringId.substring(0, stringId.length() / 2);
      var secondHalf = stringId.substring(stringId.length() / 2);
      return firstHalf.equals(secondHalf);
    }

    private boolean isSequence(long id) {
      if (id < 10) {
        return false;
      }

      var stringId = String.valueOf(id);
      for (int i = 1; i <= stringId.length() / 2; i++) {
        if (stringId.length() % i == 0) {
          // this means that the string can be split into pieces where the length of a piece is i
          var split = splitEqually(stringId, i);
          if (allPiecesAreTheSame(split)) {
            return true;
          }
        }
      }

      return false;
    }

    private long invalidSumPart1() {
      return part1InvalidIds.stream().mapToLong(i -> i).sum();
    }

    private long invalidSumPart2() {
      return part2InvalidIds.stream().mapToLong(i -> i).sum();
    }

    private List<String> splitEqually(String text, int size) {
      var result = new ArrayList<String>();
      for (int i = 0; i < text.length(); i += size) {
        result.add(text.substring(i, Math.min(text.length(), i + size)));
      }
      return result;
    }

    private boolean allPiecesAreTheSame(List<String> split) {
      var first = split.getFirst();
      return split.stream().allMatch(it -> it.equals(first));
    }
  }
}
