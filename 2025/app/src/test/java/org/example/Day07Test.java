package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day07Test {

  @Test
  void test() {
    var day = new Day07(true);
    assertEquals(21, day.beamSplits());
  }
}
