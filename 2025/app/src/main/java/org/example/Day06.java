package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day06 extends Day {

  private final List<List<String>> part1Input;
  private final List<List<Character>> part2Input;

  public Day06(boolean debug) {
    super("06", debug);

    part1Input =
        input.stream()
            .map( // map each line
                (line) ->
                    Arrays.stream(line.split(" ")) // split line by " "
                        .map(String::trim) // remove starting or ending " " from each split piece
                        .filter(s -> !s.isBlank()) // remove those that are blank
                        .toList()) // at this point it's only the numbers and operations
            .toList(); // create the 2d list of lists

    part2Input = input.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList()).toList();
  } // hey Miloš, I hope you're having a great day! :3

  @Override
  public void part1() {
    var columns = part1Input.getFirst().size();
    var lastRow = part1Input.size() - 1;
    var values = new ArrayList<Long>();
    for (int column = 0; column < columns; column++) {
      var operation = Op.of(get(lastRow, column));
      var value = operation.init();

      for (int row = 0; row < lastRow; row++) {
        var number = Integer.parseInt(String.valueOf(get(row, column)));
        value = operation.run(value, number);
      }
      values.add(value);
    }

    var sum = values.stream().mapToLong(Long::longValue).sum();
    log("day 6 part 1: " + sum);
  }

  private String get(int row, int column) {
    return part1Input.get(row).get(column);
  }

  private enum Op {
    SUM(Long::sum),
    MUL((a, b) -> a * b);
    private final BiFunction<Long, Long, Long> operation;

    Op(BiFunction<Long, Long, Long> operation) {
      this.operation = operation;
    }

    private static Op of(char c) {
      if (c == '+') {
        return Op.SUM;
      } else if (c == '*') {
        return Op.MUL;
      }
      return null;
    }

    private static Op of(String c) {
      if (c.equals("+")) {
        return Op.SUM;
      } else if (c.equals("*")) {
        return Op.MUL;
      }
      throw new IllegalArgumentException("incorrect op: " + c);
    }

    private Long run(long a, long b) {
      return operation.apply(a, b);
    }

    private Long init() {
      return switch (this) {
        case SUM -> 0L;
        case MUL -> 1L;
      };
    }
  }

  private char getC(int row, int column) {
    try {
      return part2Input.get(row).get(column);
    } catch (ArrayIndexOutOfBoundsException e) {
      return ' ';
    }
  }

  @Override
  public void part2() {
    // this is where we collect each problem (the one we need to sum at the end)
    var values = new ArrayList<Long>();

    // numbers of each problem without the operation
    var numbers = new ArrayList<Long>();

    // trivial
    Op operation;

    // the numbers, read from top to bottom
    var value = new StringBuilder();

    var columns = part2Input.getFirst().size();
    var rows = part2Input.size();
    // we start from the last column
    for (int column = columns - 1; column >= 0; column--) {
      for (int row = 0; row < rows - 1; row++) {
        var c = getC(row, column);
        value.append(c);
      }

      if (value.toString().isBlank()) {
        continue;
      }

      numbers.add(Long.parseLong(value.toString().trim()));
      value = new StringBuilder();
      operation = Op.of(getC(rows - 1, column));
      if (operation != null) {
        // when we have the operation, we can do it on the numbers
        var problem = operation.init();
        for (Long number : numbers) {
          problem = operation.run(problem, number);
        }
        // we collect the problem and clear the numbers
        values.add(problem);
        numbers.clear();
      }
    }
    var sum = values.stream().mapToLong(Long::longValue).sum();
    log("day 6 part 2: " + sum);
  }
}
