package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Day09 extends Day {

  final Layout layout;

  //  + - - > x
  //  |
  //  |
  //  V y

  public Day09(boolean debug) {
    super("09", debug);
    this.layout = new Layout(input);
  }

  @Override
  public long part1() {
    var solution = layout.findLargestArea();
    System.out.println("day 9 part 1: " + solution);
    return solution;
  }

  @Override
  public long part2() {
    var solution = layout.findLargestInArea();
    System.out.println("day 9 part 2: " + solution);
    // 4605771104 too high
    return solution;
  }

  record Point(int x, int y) {
    private Point(String s) {
      var split = s.split(",");
      this(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
  }

  record Rectangle(Point first, Point second, Point p1, Point p2, long area) {
    private Rectangle(Point first, Point second) {
      this(
          first,
          second,
          new Point(first.x, second.y),
          new Point(second.x, first.y),
          (Math.abs(first.x - second.x) + 1L) * (Math.abs(first.y - second.y) + 1L));
    }
  }

  record Line(
      Point a,
      Point b,
      boolean horizontal,
      boolean vertical,
      int lineIndex,
      int start,
      int finish) {

    private Line(Point a, Point b) {
      boolean vertical = a.x == b.x;
      boolean horizontal = a.y == b.y;
      var lineIndex = horizontal ? a.y : a.x;
      var start = vertical ? Math.min(a.y, b.y) : Math.min(a.x, b.x);
      var end = vertical ? Math.max(a.y, b.y) : Math.max(a.x, b.x);
      this(a, b, horizontal, vertical, lineIndex, start, end);
    }
  }

  record Layout(
      List<Line> horizontals, List<Line> verticals, TreeSet<Rectangle> orderedRectangles) {
    Layout(List<String> input) {
      var orderedRectangles =
          new TreeSet<Rectangle>(
              (o1, o2) -> {
                long value = o2.area - o1.area;
                if (value == 0) return 0;
                if (value > 0) return 1;
                return -1;
              });
      var lines = new ArrayList<Line>();
      for (int i = 0; i < input.size(); i++) {
        var first = new Point(input.get(i));
        for (int j = i + 1; j < input.size(); j++) {
          var second = new Point(input.get(j));
          var rectangle = new Rectangle(first, second);
          orderedRectangles.add(rectangle);
          lines.add(new Line(first, second));
        }
      }
      this(
          lines.stream().filter(Line::horizontal).toList(),
          lines.stream().filter(Line::vertical).toList(),
          orderedRectangles);
    }

    long findLargestArea() {
      return orderedRectangles.getFirst().area;
    }

    long findLargestInArea() {
      for (Rectangle rectangle : orderedRectangles) {
        if (isIn(rectangle)) {
          return rectangle.area;
        }
      }
      return 0L;
    }

    boolean isIn(Rectangle r) {
      return isIn(r.p1) && isIn(r.p2);
    }

    boolean isIn(Point p) {
      return isOnLine(p) || (isInVertically(p) && isInHorizontally(p));
    }

    boolean isOnLine(Point p) {
      return horizontals.stream()
              .filter(line -> line.start <= p.x && p.x <= line.finish)
              .anyMatch(line -> p.y == line.lineIndex)
          || verticals.stream()
              .filter(line -> line.start <= p.y && p.y <= line.finish)
              .anyMatch(line -> p.x == line.lineIndex);
    }

    boolean isInHorizontally(Point p) {
      var relevantLines =
          horizontals.stream().filter(line -> line.start <= p.x && p.x <= line.finish).toList();

      var linesBefore = relevantLines.stream().filter(line -> line.lineIndex < p.y).count();
      var linesAfter = relevantLines.stream().filter(line -> line.lineIndex > p.y).count();

      // we have to pass lines odd number of times to be inside
      return linesBefore % 2 != 0 || linesAfter % 2 != 0;
    }

    boolean isInVertically(Point p) {
      var relevantLines =
          verticals.stream().filter(line -> line.start <= p.y && p.y <= line.finish).toList();

      var linesBefore = relevantLines.stream().filter(line -> line.lineIndex < p.x).count();
      var linesAfter = relevantLines.stream().filter(line -> line.lineIndex > p.x).count();

      // we have to pass lines odd number of times to be inside
      return linesBefore % 2 != 0 || linesAfter % 2 != 0;
    }
  }
}
