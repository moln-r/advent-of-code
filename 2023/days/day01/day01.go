// Package day01 for solution and test for the daily challenge.
package day01

import (
	"aoc/internal/aoc"
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
	return "TODO"
}

func solvePart2(lines []string) string {
	return "TODO"
}
