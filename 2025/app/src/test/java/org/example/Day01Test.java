package org.example;

import junit.framework.TestCase;
import org.junit.Test;

public class Day01Test extends TestCase {

  @Test
  public void test() {
    //    var day = new Day01();
    //    day.addToCurrent(20, "L200");

    var i = -140;
    assertEquals(-1, i / 100);
    assertEquals(-40, i % 100);

    assertEquals(35, 635 % 100);
  }
}
