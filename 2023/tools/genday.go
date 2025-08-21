package main

import (
	"fmt"
	"html/template"
	"os"
	"path/filepath"
	"strconv"
)

const dayTemplate = `package day{{.Day}}

import (
	"aoc/internal/aoc"
)

func init() {
	aoc.Register({{.Day}}, solver{})
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
`

const testTemplate = `package day{{.Day}}

import (
	"os"
	"path/filepath"
	"testing"
)

func TestSample(t *testing.T) {
	b, err := os.ReadFile(filepath.Join("..", "..", "testdata", "day{{.DayPadded}}_sample.txt"))
	if err != nil { t.Fatal(err) }
	lines := splitLines(string(b))
	if got := solvePart1(lines); got == "TODO" {
		t.Fatalf("part1 not implemented, got %q", got)
	}
	if got := solvePart2(lines); got == "TODO" {
		t.Fatalf("part2 not implemented, got %q", got)
	}
}
`

func main() {
	if len(os.Args) != 2 {
		fmt.Println("Usage: genday <day>")
		os.Exit(1)
	}
	day, err := strconv.Atoi(os.Args[1])
	if err != nil || day < 1 || day > 25 {
		fmt.Println("day must be 1..25")
		os.Exit(1)
	}
	dayPadded := fmt.Sprintf("%02d", day)

	// create days/dayNN
	dir := filepath.Join("days", "day"+dayPadded)
	if err := os.MkdirAll(dir, 0755); err != nil {
		panic(err)
	}

	// write .go
	writeTemplate(filepath.Join(dir, "day"+dayPadded+".go"), dayTemplate, day, dayPadded)
	// write _test.go
	writeTemplate(filepath.Join(dir, "day"+dayPadded+"_test.go"), testTemplate, day, dayPadded)

	// create empty input + sample files
	os.WriteFile(filepath.Join("inputs", "day"+dayPadded), []byte{}, 0644)
	os.WriteFile(filepath.Join("testdata", "day"+dayPadded+"_sample"), []byte{}, 0644)

	fmt.Printf("Scaffolded day %d in %s\n", day, dir)
}

func writeTemplate(path, src string, day int, dayPadded string) {
	tmpl := template.Must(template.New("").Parse(src))
	f, err := os.Create(path)
	if err != nil {
		panic(err)
	}
	defer f.Close()
	tmpl.Execute(f, map[string]string{
		"Day":       strconv.Itoa(day),
		"DayPadded": dayPadded,
	})
}
