package org.example;

public class Day07 extends Day {

  private static final char START = 'S';
  private static final char BEAM = '|';
  private static final char SPLITTER = '^';
  private static final char EMPTY = '.';

  private final char[][] manifold;
  private final int rows;
  private final int columns;

  public Day07(boolean debug) {
    super("07", debug);

    this.rows = input.size();
    this.columns = input.getFirst().length();
    manifold = new char[rows][columns];
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        manifold[row][column] = input.get(row).charAt(column);
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
        char c = manifold[row][column];
        if (c == START) {
          // start position, put a line below
          manifold[row + 1][column] = BEAM;
          //        } else if (c == BEAM) {
          //           beam, if below is empty, that becomes a beam
          //          if (row < rows - 1 && manifold[row + 1][column] == EMPTY) {
          //            manifold[row + 1][column] = BEAM;
          //          }
        } else if (c == SPLITTER) {
          // splitter, if there is beam above it, that splits

          if (manifold[row - 1][column] == BEAM) {
            manifold[row][column - 1] = BEAM;
            manifold[row][column + 1] = BEAM;
            // this is what we need to count
            splits++;
          }
        } else if (row > 0 && manifold[row - 1][column] == BEAM) {
          // if above is a beam and current is empty, it comes down
          if (manifold[row][column] == EMPTY) {
            manifold[row][column] = BEAM;
          }
        }
      }
      //      print();
    }

    return splits;
  }

  @Override
  public void part2() {}

  private void print() {
    System.out.println();
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        System.out.print(manifold[row][column]);
      }
      System.out.println();
    }
    System.out.println();
  }
}
