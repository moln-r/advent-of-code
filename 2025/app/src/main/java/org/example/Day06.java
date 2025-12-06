package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day06 extends Day {

  private final List<List<String>> parsedInput;

  public Day06(boolean debug) {
    super("06", debug);

    parsedInput =
        input.stream()
            .map( // map each line
                (line) ->
                    Arrays.stream(line.split(" ")) // split line by " "
                        .map(String::trim) // remove starting or ending " " from each split piece
                        .filter(s -> !s.isBlank()) // remove those that are blank
                        .toList()) // at this point it's only the numbers and operations
            .toList(); // create the 2d list of lists
  } // hey Miloš, I hope you're having a great day! :3

  @Override
  public void part1() {
    var columns = parsedInput.getFirst().size();
    var lastRow = parsedInput.size() - 1;
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
    System.out.println("day 6 part 1: " + sum);
  }

  private String get(int row, int column) {
    return parsedInput.get(row).get(column);
  }

  private enum Op {
    SUM(Long::sum),
    MUL((a, b) -> a * b);
    private final BiFunction<Long, Long, Long> operation;

    Op(BiFunction<Long, Long, Long> operation) {
      this.operation = operation;
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

  @Override
  public void part2() {}
}
