// Package day01 for solution and test for the daily challenge.
package day01

import (
	"aoc/internal/aoc"
	"fmt"
	"strconv"
	"unicode"
)

func init() {
	aoc.Register(01, solver{})
}

type solver struct{}

func (solver) Solve(input string) (string, string, error) {
	lines := aoc.ReadInputAsLines(input)
	return solvePart1(lines), solvePart2(lines), nil
}

func solvePart1(lines []string) string {
	sum := 0
	for _, line := range lines {
		sum = sum + numFromLine(line)
	}
	return strconv.Itoa(sum)
}

func solvePart2(lines []string) string {
	fmt.Println(lines[0])
	return "TODO"
}

func numFromLine(line string) int {
	if line == "" {
		return 0
	}

	first := 0
	last := 0

	for _, r := range line {
		num, err := charAsNum(r)
		if err == nil {
			if first == 0 {
				first = num
			}
			last = num
		}
	}

	// fmt.Printf("First number is %d, last one is %d\n", first, last)
	return 10*first + last
}

func charAsNum(c rune) (int, error) {
	if unicode.IsDigit(c) {
		return int(c - '0'), nil
	}
	return 0, fmt.Errorf("not numeric")
}
