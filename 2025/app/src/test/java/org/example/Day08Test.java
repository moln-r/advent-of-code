package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day08Test {

  private Day08 day;

  @BeforeEach
  void setUp() {
    day = new Day08(true);
  }

  @Test
  void testPart1() {
    var distances = day.getDistancesSorted();
    day.connect(distances, 10);

    // After making the ten shortest connections, there are 11 circuits: one circuit which contains
    // 5 junction boxes, one circuit which contains 4 junction boxes, two circuits which contain 2
    // junction boxes each, and seven circuits which each contain a single junction box.

    var circuits = day.getCircuits();
    assertEquals(11, circuits.size());

    assertEquals(1, circuits.stream().filter(circuit -> circuit.size() == 5).count());
    assertEquals(1, circuits.stream().filter(circuit -> circuit.size() == 4).count());
    assertEquals(2, circuits.stream().filter(circuit -> circuit.size() == 2).count());
    assertEquals(7, circuits.stream().filter(circuit -> circuit.size() == 1).count());
  }

  @Test
  void testPart2() {
    var solution = day.connect(day.getDistancesSorted(), Integer.MAX_VALUE);
    assertEquals(25272L, solution);
  }
}
