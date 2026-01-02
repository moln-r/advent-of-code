package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day10 extends Day {

  private static final char OFF = '.';
  private static final char ON = '#';

  final List<Machine> machines;

  public Day10(boolean debug) {
    super("10", debug);

    this.machines = new ArrayList<>();

    for (String line : input) {
      var splitBySpace = line.split(" ");
      var lightIndex = 0;
      var schematicIndex = splitBySpace.length - 1;
      var light =
          new Light(splitBySpace[lightIndex].substring(1, splitBySpace[lightIndex].length() - 1));

      var buttons = new ArrayList<Button>();
      for (int i = lightIndex + 1; i < schematicIndex; i++) {
        buttons.add(new Button(splitBySpace[i]));
      }

      var schematicString =
          splitBySpace[schematicIndex].substring(1, splitBySpace[schematicIndex].length() - 1);
      var schematic = new Schematic(schematicString);

      this.machines.add(new Machine(light, buttons, schematic));
    }
  }

  record Light(String state) {
    String init() {
      return state.replace(ON, OFF);
    }
  }

  record Button(List<Integer> switches) {
    Button(String s) {
      this(
          Arrays.stream(s.split(","))
              .map(num -> num.replace("(", "").replace(")", ""))
              .map(Integer::parseInt)
              .toList());
    }

    String use(String light) {
      var state = light.toCharArray();
      for (Integer index : switches) {
        if (state[index] == ON) {
          state[index] = OFF;
        } else if (state[index] == OFF) {
          state[index] = ON;
        }
      }
      return new String(state);
    }
  }

  record Schematic(List<Integer> schemas) {
    Schematic(String s) {
      this(Arrays.stream(s.split(",")).map(Integer::parseInt).toList());
    }
  }

  record Machine(Light light, List<Button> buttons, Schematic schematics) {

    public List<List<Button>> combinations(int size) {
      List<List<Button>> result = new ArrayList<>();
      backtrack(size, 0, new ArrayList<>(), result);
      return result;
    }

    private void backtrack(int size, int start, List<Button> current, List<List<Button>> result) {
      if (current.size() == size) {
        result.add(new ArrayList<>(current));
        return;
      }

      for (int i = start; i < buttons.size(); i++) {
        current.add(buttons.get(i));
        backtrack(size, i + 1, current, result);
        current.removeLast();
      }
    }

    int solve() {
      for (int i = 1; i <= buttons.size(); i++) {
        var combinations = combinations(i);
        for (List<Button> combination : combinations) {
          var state = this.light.init();
          for (Button button : combination) {
            state = button.use(state);
          }
          if (state.equals(light.state())) {
            return i;
          }
        }
      }
      throw new RuntimeException("machine could not find solution");
    }
  }

  @Override
  public long part1() {
    AtomicLong solution = new AtomicLong(0L);
    machines.forEach(m -> solution.addAndGet(m.solve()));
    System.out.println("day 10 part 1: " + solution);
    return solution.get();
  }

  @Override
  public long part2() {
    return 0;
  }
}
