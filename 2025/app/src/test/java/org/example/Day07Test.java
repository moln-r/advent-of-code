package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day07Test {

  private Day07 day;

  @BeforeEach
  void setUp() {
    day = new Day07(true);
  }

  @Test
  void testPart1() {
    assertEquals(21, day.beamSplits());
  }

  @Test
  void testPart2() {
    day.beamSplits(); // to trigger the splits to happen
    day.print();
    //    assertEquals(40, day.walk(0, 7, 0));
    assertEquals(40, day.countTimelines());
  }
}
