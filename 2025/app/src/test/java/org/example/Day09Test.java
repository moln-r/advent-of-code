package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.example.Day09.Layout;
import org.example.Day09.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day09Test {

  private Day09 day;
  private Layout layout;

  @BeforeEach
  void setUp() {
    day = new Day09(true);
    layout = day.layout;
  }

  @Test
  void testPart1() {
    var area = layout.findLargestArea();
    assertEquals(50, area);
  }

  @Test
  void testPart2() {
    var area = layout.findLargestInArea();
    assertEquals(24, area);
  }

  @Test
  void testInHorizontally() {
    assertTrue(layout.isInHorizontally(new Point(5, 5)));
    assertFalse(layout.isInHorizontally(new Point(0, 5)));
  }

  @Test
  void testInVertically() {
    assertTrue(layout.isInVertically(new Point(1, 8)));
    assertFalse(layout.isInVertically(new Point(1, 5)));
  }

  @Test
  void testPointIn() {
    var all = new ArrayList<Point>();
    for (int i = 1; i < 12; i++) {
      for (int j = 1; j < 12; j++) {
        all.add(new Point(i, j));
      }
    }
    var inPoints =
        List.of(
            new Point(2, 3),
            new Point(2, 4),
            new Point(2, 5),
            new Point(3, 3),
            new Point(3, 4),
            new Point(3, 5),
            new Point(4, 3),
            new Point(4, 4),
            new Point(4, 5),
            new Point(5, 3),
            new Point(5, 4),
            new Point(5, 5),
            new Point(6, 3),
            new Point(6, 4),
            new Point(6, 5),
            new Point(7, 1),
            new Point(7, 2),
            new Point(7, 3),
            new Point(7, 4),
            new Point(7, 5),
            new Point(8, 1),
            new Point(8, 2),
            new Point(8, 3),
            new Point(8, 4),
            new Point(8, 5),
            new Point(9, 1),
            new Point(9, 2),
            new Point(9, 3),
            new Point(9, 4),
            new Point(9, 5),
            new Point(9, 6),
            new Point(9, 7),
            new Point(10, 1),
            new Point(10, 2),
            new Point(10, 3),
            new Point(10, 4),
            new Point(10, 5),
            new Point(10, 6),
            new Point(10, 7),
            new Point(11, 1),
            new Point(11, 2),
            new Point(11, 3),
            new Point(11, 4),
            new Point(11, 5),
            new Point(11, 6),
            new Point(11, 7));

    all.removeAll(inPoints);

    for (Point p : inPoints) {
      assertTrue(layout.isIn(p), "not in: " + p);
    }
    for (Point p : all) {
      assertFalse(layout.isIn(p), "in: " + p);
    }

    assertTrue(inPoints.stream().allMatch(layout::isIn));
  }
}

// indexes here are fucked, they start from 0...
//  x 012345678901.
// y
// 0  ..............
// 1  .......#XXX#..
// 2  .......X...X..
// 3  ..#XXXX#...X..
// 4  ..X........X..
// 5  ..#XXXXXX#.X..
// 6  .........X.X..
// 7  .........#X#..
// 8  ..............
