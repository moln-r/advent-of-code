package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Day08 extends Day {

  private final List<JunctionBox> junctionBoxes;
  private final List<Circuit> circuits;

  public Day08(boolean debug) {
    super("08", debug);

    this.junctionBoxes = input.stream().map(s -> s.split(",")).map(JunctionBox::new).toList();
    this.circuits = new ArrayList<>();
    this.circuits.addAll(junctionBoxes.stream().map(JunctionBox::toSet).map(Circuit::new).toList());
  }

  protected List<Circuit> getCircuits() {
    return circuits;
  }

  protected TreeSet<Distance> getDistancesSorted() {
    var distanceComparator = Comparator.comparing(Distance::distance);
    var all = new TreeSet<>(distanceComparator);
    for (int i = 0; i < junctionBoxes.size(); i++) {
      var c1 = junctionBoxes.get(i);
      for (int j = i + 1; j < junctionBoxes.size(); j++) {
        var c2 = junctionBoxes.get(j);
        var distance = new Distance(c1, c2, c1.getDistance(c2));
        all.add(distance);
      }
    }
    return all;
  }

  protected void connect(TreeSet<Distance> distances, int limit) {
    var count = 0;
    for (Distance distance : distances) {
      connect(distance);
      count++;
      if (count == limit) {
        break;
      }
    }
  }

  private void connect(Distance distance) {
    var junctionBox1 = distance.jb1();
    var circuit1 =
        circuits.stream()
            .filter(c -> c.contains(junctionBox1))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find circuit with " + junctionBox1));

    var junctionBox2 = distance.jb2();
    var circuit2 =
        circuits.stream()
            .filter(c -> c.contains(junctionBox2))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find circuit with " + junctionBox2));

    if (!circuit1.equals(circuit2)) {
      circuit1.mergeWith(circuit2);
      this.circuits.remove(circuit2);
    }
  }

  @Override
  public long part1() {
    var distances = getDistancesSorted();
    connect(distances, 1000);

    circuits.sort((o1, o2) -> o2.size() - o1.size());

    long solution = 1L;
    for (int i = 0; i < 3 && i < circuits.size(); i++) {
      solution *= circuits.get(i).positions().size();
    }
    System.out.println("day 8 part 1: " + solution);
    return solution;
  }

  @Override
  public long part2() {
    return 0;
  }

  protected static class JunctionBox {
    long x;
    long y;
    long z;

    JunctionBox(String[] coordinates) {
      this.x = Long.parseLong(coordinates[0]);
      this.y = Long.parseLong(coordinates[1]);
      this.z = Long.parseLong(coordinates[2]);
    }

    double getDistance(JunctionBox other) {
      return Math.sqrt(
          Math.powExact(this.x - other.x, 2)
              + Math.powExact(this.y - other.y, 2)
              + Math.powExact(this.z - other.z, 2));
    }

    HashSet<JunctionBox> toSet() {
      var set = new HashSet<JunctionBox>();
      set.add(this);
      return set;
    }

    @Override
    public String toString() {
      return "(" + x + ", " + y + ", " + z + ')';
    }
  }

  protected record Circuit(Set<JunctionBox> positions) {
    boolean contains(JunctionBox bp) {
      return positions.contains(bp);
    }

    int size() {
      return positions.size();
    }

    public void mergeWith(Circuit another) {
      this.positions().addAll(another.positions());
    }
  }

  protected record Distance(JunctionBox jb1, JunctionBox jb2, double distance) {
    @Override
    public String toString() {
      return jb1 + " - " + jb2 + ": " + distance;
    }
  }
}
