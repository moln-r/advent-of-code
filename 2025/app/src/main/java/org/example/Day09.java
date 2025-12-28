package org.example;

import java.util.ArrayList;
import java.util.List;

public class Day09 extends Day {

  private final List<Rectangle> rectangles;

  public Day09(boolean debug) {
    super("09", debug);

    this.rectangles = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      var first = new Point(input.get(i));
      for (int j = i + 1; j < input.size(); j++) {
        var second = new Point(input.get(j));
        rectangles.add(new Rectangle(first, second));
      }
    }
  }

  @Override
  public long part1() {
    var solution = findLargestArea();
    System.out.println("day 9 part 1: " + solution);
    return solution;
  }

  protected long findLargestArea() {
    return rectangles.stream().map(Rectangle::area).max(Long::compareTo).orElse(0L);
  }

  @Override
  public long part2() {
    var solution = 0L;
    System.out.println("day 9 part 2: " + solution);
    return solution;
  }

  private record Point(int x, int y) {
    private Point(String s) {
      var split = s.split(",");
      this(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
  }

  private record Rectangle(Point first, Point second, Point p1, Point p2, long area) {
    private Rectangle(Point first, Point second) {
      this(
          first,
          second,
          new Point(first.x, second.y),
          new Point(second.x, first.y),
          (Math.abs(first.x - second.x) + 1L) * (Math.abs(first.y - second.y) + 1L));
    }
  }
}
