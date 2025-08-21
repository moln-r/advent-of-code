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
		sum = sum + numFromLine(line, false)
	}
	return strconv.Itoa(sum)
}

func solvePart2(lines []string) string {
	sum := 0
	for _, line := range lines {
		sum = sum + numFromLine(line, true)
	}
	return strconv.Itoa(sum)
}

func numFromLine(line string, writtenNums bool) int {
	if line == "" {
		return 0
	}

	first := 0
	last := 0

	for i, r := range line {
		num, err := charAsNum(r)
		if err == nil {
			if first == 0 {
				first = num
			}
			last = num
		}
		if writtenNums {
			num, err = numFromThisChar([]rune(line)[i:])
			if err == nil {
				if first == 0 {
					first = num
				}
				last = num
			}
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

var numbers = [][]rune{
	{'o', 'n', 'e'},
	{'t', 'w', 'o'},
	{'t', 'h', 'r', 'e', 'e'},
	{'f', 'o', 'u', 'r'},
	{'f', 'i', 'v', 'e'},
	{'s', 'i', 'x'},
	{'s', 'e', 'v', 'e', 'n'},
	{'e', 'i', 'g', 'h', 't'},
	{'n', 'i', 'n', 'e'},
}

func numFromThisChar(substring []rune) (int, error) {
	for numberIndex, number := range numbers {
		hit := true
		if len(substring) < len(number) {
			// number cannot fit, we skip it
			continue
		}

		for i := range number {
			if substring[i] == number[i] {
				continue
			} else {
				hit = false
			}
		}
		if hit {
			return numberIndex + 1, nil
		}
	}
	return 0, fmt.Errorf("no number starts from this character")
}
