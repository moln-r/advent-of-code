// Package aoc in cmd provides main function, registry for solutions and io reading.
package aoc

import (
	"aoc/internal/aoc"
	"flag"
	"fmt"
	"log"
	"path/filepath"
)

func main() {
	day := flag.Int("day", 1, "day number (1..25")
	part := flag.Int("part", 0, "part (1|2|0=both)")
	in := flag.String("in", "", "path to input (defaults to input/dayNN)")
	flag.Parse()
	fmt.Println("running with day", *day, "part", *part, "input", *in)

	s, ok := aoc.Get(*day)
	if !ok {
		log.Fatalf("no solution for day %d", *day)
	}

	path := *in
	if path == "" {
		path = filepath.Join("inputs", fmt.Sprintf("day%02d", *day))
	}
	input, err := aoc.ReadInput(path)
	if err != nil {
		log.Fatal(err)
	}

	part1, part2, err := s.Solve(input)
	if err != nil {
		log.Fatal(err)
	}

	switch *part {
	case 1:
		fmt.Println(part1)
	case 2:
		fmt.Println(part2)
	default:
		fmt.Printf("Part 1: %s\nPart 2: %s\n", part1, part2)
	}
}
