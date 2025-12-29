package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day10Test {

  private Day10 day;

  @BeforeEach
  void setUp() {
    day = new Day10(true);
  }

  @Test
  void test() {
    Assertions.assertEquals(1, 11);
  }
}
