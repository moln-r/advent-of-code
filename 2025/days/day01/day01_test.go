package day01

import (
	"aoc/internal/aoc"
	"testing"
)

func TestSample(t *testing.T) {
	lines := aoc.ReadInputAsLines("day01_sample")
	if got := solvePart1(lines); got == "TODO" {
		t.Fatalf("part1 not implemented, got %q", got)
	}
	if got := solvePart2(lines); got == "TODO" {
		t.Fatalf("part2 not implemented, got %q", got)
	}
}
