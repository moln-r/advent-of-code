package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Day03Test {
  private static final Day03 DAY = new Day03(true);

  static Stream<Arguments> testArguments() {
    return Stream.of(
        Arguments.of("818181911112111", 888911112111L),
        Arguments.of("987654321111111", 987654321111L),
        Arguments.of("234234234234278", 434234234278L),
        Arguments.of("811111111111119", 811111111119L));
  }

  @ParameterizedTest
  @MethodSource("testArguments")
  void testFancyJolt(String line, long expected) {
    var jolt = DAY.fancyJolt(line);
    assertEquals(expected, jolt);
  }
}
