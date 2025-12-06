package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Day {

  protected final List<String> input;
  private final boolean debug;

  public Day(String day, boolean debug) {
    this.input = readFile(day);
    this.debug = debug;
  }

  public abstract void part1();

  public abstract void part2();

  private List<String> readFile(String name) {
    try {
      var resource = getClass().getClassLoader().getResource(name);
      return Files.readAllLines(Path.of(resource.toURI()));
    } catch (Exception e) {
      throw new RuntimeException("could not read input");
    }
  }

  protected void log(String message) {
    System.out.println(message);
  }

  protected void debug(String message) {
    if (debug) {
      log(message);
    }
  }
}
