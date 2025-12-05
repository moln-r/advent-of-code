package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Day05 extends Day {

  private final List<Range> ranges;

  private final List<Long> toCheck;

  public Day05(boolean debug) {
    super("05", debug);

    this.ranges = new ArrayList<>();
    this.toCheck = new ArrayList<>();

    Consumer<String> addToRange =
        line -> {
          var split = line.split("-");
          ranges.add(Range.from(Long.parseLong(split[0]), Long.parseLong(split[1])));
        };
    Consumer<String> addToCheck =
        line -> {
          toCheck.add(Long.parseLong(line));
        };

    input.forEach(
        line -> {
          if (line == null || line.isEmpty()) {
            return;
          }
          if (line.contains("-")) {
            addToRange.accept(line);
          } else {
            addToCheck.accept(line);
          }
        });
  }

  @Override
  public void part1() {
    var solution =
        toCheck.stream().filter(l -> ranges.stream().anyMatch(range -> range.in(l))).count();
    System.out.println("day 5 part 1: " + solution);
  }

  @Override
  public void part2() {
    // we start with the original list (sorted) and try to merge items together
    var mergedRanges = ranges.stream().sorted().collect(Collectors.toList());
    while (true) {
      // new list because we remove one item in mergeRanges
      var newlyMerged = mergeRanges(new ArrayList<>(mergedRanges));

      if (newlyMerged.size() == mergedRanges.size()) {
        // we are done, can't merge more
        break;
      }
      mergedRanges = newlyMerged;
    }

    System.out.println(
        "day 5 part 2: " + mergedRanges.stream().map(Range::size).mapToLong(i -> i).sum());
  }

  private List<Range> mergeRanges(List<Range> ranges) {
    var mergedRanges = new ArrayList<Range>();
    mergedRanges.add(ranges.removeFirst());

    for (Range range : ranges) {
      boolean noMerge = true;
      for (Range merged : mergedRanges) {
        if (merged.overlaps(range)) {
          merged.merge(range);
          noMerge = false;
          break;
        }
      }
      if (noMerge) {
        // if we could not merge into any existing, we add it to the list
        mergedRanges.add(range);
      }
    }
    return mergedRanges;
  }

  private static class Range implements Comparable<Range> {

    private long a;
    private long b;

    public Range(long a, long b) {
      this.a = a;
      this.b = b;
    }

    public static Range from(long a, long b) {
      return new Range(a, b);
    }

    private boolean in(long x) {
      return x >= a && x <= b;
    }

    private boolean overlaps(Range range) {
      if (this.b + 1 == range.a || range.b + 1 == this.a) {
        return true;
      }
      return this.in(range.a) || this.in(range.b);
    }

    private void merge(Range other) {
      this.a = Math.min(this.a, other.a);
      this.b = Math.max(this.b, other.b);
    }

    private long size() {
      return b - a + 1;
    }

    @Override
    public String toString() {
      return a + "\t-\t" + b;
    }

    @Override
    public int compareTo(Range o) {
      return Long.compare(this.a, o.a);
    }
  }
}
