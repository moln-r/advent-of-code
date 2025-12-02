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
    long sum = this.ranges.stream().map(Range::invalidSum).mapToLong(Long::longValue).sum();
    log("day 2 part 1: " + sum);
  }

  @Override
  public void part2() {}

  private static class Range {
    private final long x;
    private final long y;
    private final List<Long> invalidIds;

    public Range(String[] range) {
      this.x = Long.parseLong(range[0]);
      this.y = Long.parseLong(range[1]);

      this.invalidIds = getInvalidIds();
    }

    private List<Long> getInvalidIds() {
      var invalidIds = new ArrayList<Long>();
      for (long i = x; i <= y; i++) {
        if (isInvalid(i)) {
          invalidIds.add(i);
        }
      }
      return invalidIds;
    }

    private boolean isInvalid(long id) {
      var stringId = String.valueOf(id);
      if (stringId.length() % 2 != 0) {
        return false;
      }
      var firstHalf = stringId.substring(0, stringId.length() / 2);
      var secondHalf = stringId.substring(stringId.length() / 2);
      return firstHalf.equals(secondHalf);
    }

    private long invalidSum() {
      return invalidIds.stream().mapToLong(i -> i).sum();
    }
  }
}
