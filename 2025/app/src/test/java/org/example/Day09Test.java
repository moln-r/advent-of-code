package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day09Test {

  private Day09 day;

  @BeforeEach
  void setUp() {
    day = new Day09(true);
  }

  @Test
  void testPart1() {
    var area = day.findLargestArea();
    assertEquals(50, area);
  }

  @Test
  void testPart2() {}
}
