package org.example;

public class Day07 extends Day {

  private static final char START = 'S';
  private static final char BEAM = '|';
  private static final char SPLITTER = '^';
  private static final char EMPTY = '.';

  private final C[][] manifold;
  private final int rows;
  private final int columns;

  public Day07(boolean debug) {
    super("07", debug);

    this.rows = input.size();
    this.columns = input.getFirst().length();
    this.manifold = new C[rows][columns];
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        manifold[row][column] = new C(input.get(row).charAt(column), 0L);
      }
    }
  }

  @Override
  public void part1() {
    System.out.println("day 7 part 1: " + beamSplits());
  }

  protected long beamSplits() {
    long splits = 0;

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        char c = manifold[row][column].c;
        if (c == START) {
          // start position, put a line below
          manifold[row + 1][column].c = BEAM;
        } else if (c == SPLITTER) {
          // splitter, if there is beam above it, that splits

          if (manifold[row - 1][column].c == BEAM) {
            manifold[row][column - 1].c = BEAM;
            manifold[row][column + 1].c = BEAM;
            // this is what we need to count for part 1
            splits++;
          }
        } else if (row > 0 && manifold[row - 1][column].c == BEAM) {
          // if above is a beam and current is empty, it comes down
          if (manifold[row][column].c == EMPTY) {
            manifold[row][column].c = BEAM;
          }
        }
      }
      //      print();
    }

    return splits;
  }

  @Override
  public void part2() {
    beamSplits(); // to trigger the splits to happen

    System.out.println("day 7 part 2: " + countTimelines());
    // 296320489 - too low
    //     296320489
    //    print();
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        manifold[row][column] = new C(input.get(row).charAt(column), 0);
      }
    }
  }

  protected long countTimelines_orig() {
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        var current = manifold[row][column];

        if (current.c == START) {
          current.add(1);
          break; // here we can break, because the start row has nothing else
        } else if (current.c == BEAM) {
          current.add(manifold[row - 1][column].i);
        } else if (current.c == SPLITTER) {
          var above = manifold[row - 1][column].i;
          manifold[row][column - 1].add(above);
          manifold[row][column + 1].add(above);
        }
      }
    }

    var count = 0L;
    int lastRow = manifold.length - 1;
    for (int column = 0; column < manifold[lastRow].length; column++) {
      count += manifold[lastRow][column].i;
    }

    return count;
  }

  protected long countTimelines() {
    for (int row = 0; row < rows; row++) {
      for (int column = columns - 1; column >= 0; column--) {
        var current = manifold[row][column];

        if (current.c == START) {
          current.add(1);
          break; // here we can break, because the start row has nothing else
        } else if (current.c == BEAM) {
          current.add(manifold[row - 1][column].i);
        } else if (current.c == SPLITTER) {
          var above = manifold[row - 1][column].i;
          manifold[row][column - 1].add(above);
          manifold[row][column + 1].add(above);
        }
      }
    }

    var count = 0L;
    int lastRow = manifold.length - 1;
    for (int column = 0; column < manifold[lastRow].length; column++) {
      count += manifold[lastRow][column].i;
    }

    return count;
  }

  // fancy, works in test, but the file is too big
  long walk(int row, int column, long count) {
    // exit strategy first
    if (row == rows - 1) {
      debug("done - at row " + row + ", column " + column);
      return count + 1;
    }

    int nextRow = row + 1;
    char next = manifold[nextRow][column].c;
    if (next == BEAM) {
      return walk(nextRow, column, count);
    } else if (next == SPLITTER) {
      var left = walk(nextRow, column - 1, count);
      var right = walk(nextRow, column + 1, count);
      return left + right;
    } else {
      throw new RuntimeException("walk error at row " + row + ", column " + column);
    }
  }

  void print() {
    System.out.println();
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        var current = manifold[row][column];
        //        if (current.c == EMPTY || current.c == SPLITTER) {
        System.out.print(current.c);
        //        } else {
        //          System.out.print(manifold[row][column].i);
        //        }
      }
      System.out.println();
    }
    System.out.println();
  }

  private static class C {
    private char c;
    private long i;

    C(char c, long i) {
      this.c = c;
      this.i = i;
    }

    void add(long i) {
      this.i += i;
    }
  }
}
