package org.example;

public class Day09 extends Day {

  public Day09(boolean debug) {
    super("09", debug);
  }

  @Override
  public long part1() {
    var solution = findLargestArea();
    System.out.println("day 9 part 1: " + solution);
    return solution;
  }

  protected long findLargestArea() {
    var area = 0L;
    for (int i = 0; i < input.size(); i++) {
      var first = input.get(i).split(",");
      for (int j = i + 1; j < input.size(); j++) {
        var second = input.get(j).split(",");

        var x = Math.abs(Long.parseLong(first[0]) - Long.parseLong(second[0])) + 1L;
        var y = Math.abs(Long.parseLong(first[1]) - Long.parseLong(second[1])) + 1L;
        var newArea = x * y;
        if (newArea > area) {
          area = newArea;
        }
      }
    }
    return area;
  }

  @Override
  public long part2() {
    return 0;
  }
}
