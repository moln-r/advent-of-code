package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 extends Day {

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

  record Light(String state) {}

  record Button(List<Integer> switches) {
    Button(String s) {
      this(
          Arrays.stream(s.split(","))
              .map(num -> num.replace("(", "").replace(")", ""))
              .map(Integer::parseInt)
              .toList());
    }
  }

  record Schematic(List<Integer> schemas) {
    Schematic(String s) {
      this(Arrays.stream(s.split(",")).map(Integer::parseInt).toList());
    }
  }

  record Machine(Light light, List<Button> buttons, Schematic schematics) {}

  @Override
  public long part1() {
    return 0;
  }

  @Override
  public long part2() {
    return 0;
  }
}
