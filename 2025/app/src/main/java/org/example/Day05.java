package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
          ranges.add(new Range(Long.parseLong(split[0]), Long.parseLong(split[1])));
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
  public void part2() {}

  private record Range(long a, long b) {
    private boolean in(long x) {
      return x >= a && x <= b;
    }
  }
}
