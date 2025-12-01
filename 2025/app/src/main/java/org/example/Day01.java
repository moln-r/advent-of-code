package org.example;

public class Day01 {

    public static void part1() {
        var input = App.readFile("01");
        int counter = 0;
        int solution = 0;
        for (String line : input) {
            if (addToCurrent(counter, line)) {
                solution++;
            }
        }
        System.out.println("solution part 1: " + solution);
    }

    private static boolean addToCurrent(int current, String line) {
        if (line.startsWith("L")) {
            current -= Integer.valueOf(line.substring(1));
            if (current < 0) {
                current += 100;
            }
        } else if (line.startsWith("R")) {
            current += Integer.valueOf(line.substring(1));
            if (current >= 100) {
                current -= 100;
            }
        } else {
            throw new RuntimeException("part 1 current calculation error");
        }
        return current == 0;
    }
}
