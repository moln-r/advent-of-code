package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Day04 extends Day {

  private static final String EMPTY = ".";
  private static final String ROLL = "@";
  private static final String REMOVABLE = "x";

  private static final long PART_1_LIMIT = 4L;

  public Day04(boolean debug) {
    super("04", debug);
  }

  /*
  my directions:
  -->
  |
  V


   */

  @Override
  public long part1() {
    int accessibleCount = 0;
    for (int x = 0; x < input.size(); x++) {
      for (int y = 0; y < input.get(x).length(); y++) {
        if (!String.valueOf(input.get(x).charAt(y)).equals(ROLL)) {
          continue;
        }
        if (accessible(x, y)) {
          accessibleCount++;
        }
      }
    }
    log("day 4 part 1: " + accessibleCount);
    return accessibleCount;
  }

  @Override
  public long part2() {
    int markCount = 0;
    int mark = -1;
    while (mark != 0) {
      mark = markRemovable();
      remove();
      markCount += mark;
    }
    log("day 4 part 2: " + markCount);
    return markCount;
  }

  private int markRemovable() {
    int marks = 0;
    for (int x = 0; x < input.size(); x++) {
      for (int y = 0; y < input.get(x).length(); y++) {
        if (!String.valueOf(input.get(x).charAt(y)).equals(ROLL)) {
          continue;
        }
        if (accessible(x, y)) {
          var newLine = new StringBuilder(input.get(x));
          newLine.setCharAt(y, REMOVABLE.toCharArray()[0]);
          this.input.set(x, newLine.toString());
          marks++;
        }
      }
    }
    return marks;
  }

  private void remove() {
    var updated = input.stream().map(line -> line.replace(REMOVABLE, EMPTY)).toList();
    input.clear();
    input.addAll(updated);
  }

  private boolean accessible(int x, int y) {
    var rolls = countRolls(x, y);
    return rolls < PART_1_LIMIT;
  }

  private long countRolls(int x, int y) {
    return Arrays.stream(Dir.values())
        .map(d -> d.move(x, y))
        .map(cords -> at(cords[0], cords[1]))
        .filter(Objects::nonNull)
        .filter(s -> s.equals(ROLL) || s.equals(REMOVABLE))
        .count();
  }

  private String at(int a, int b) {
    if (a < 0 || b < 0) {
      return null;
    }
    if (a >= this.input.size()) {
      return null;
    }
    if (b >= this.input.getFirst().length()) {
      return null;
    }
    return String.valueOf(this.input.get(a).charAt(b));
  }

  private enum Dir {
    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    LEFT(0, -1),
    RIGHT(0, 1),
    DOWN_LEFT(1, -1),
    DOWN(1, 0),
    DOWN_RIGHT(1, 1);

    private final int xDiff;
    private final int yDiff;

    Dir(int xDiff, int yDiff) {
      this.xDiff = xDiff;
      this.yDiff = yDiff;
    }

    private Integer[] move(int x, int y) {
      return new Integer[] {x + xDiff, y + yDiff};
    }
  }
}
