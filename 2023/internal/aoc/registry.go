// Package aoc provides functionality to register and retrieve solvers for Advent of Code challenges.
package aoc

// Solver is an interface that defines the method to solve Advent of Code challenges.
type Solver interface {
	Solve(input string) (string, string, error) // part 1, part 2
}

var registry = map[int]Solver{}

// Register registers a solver for a specific day.
func Register(day int, s Solver) {
	registry[day] = s
}

// Get a solver for a specific day.
func Get(day int) (Solver, bool) {
	s, ok := registry[day]
	return s, ok
}
