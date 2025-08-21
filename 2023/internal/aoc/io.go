// Package aoc provides functionality to read intput files for Advent of Code challenges.
package aoc

import (
	"os"
	"strings"
)

// ReadInput reads the input file for a given path and returns its content as a string.
func ReadInput(path string) (string, error) {
	b, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}
	return strings.TrimRight(string(b), "\n\r"), nil
}

func ReadInputAsLines(s string) []string {
	if s == "" {
		return nil
	}
	var out []string
	start := 0
	for i := 0; i < len(s); i++ {
		if s[i] == '\n' {
			out = append(out, trimCR(s[start:i]))
			start = i + 1
		}
	}
	if start <= len(s)-1 {
		out = append(out, trimCR(s[start:]))
	}
	return out
}

func trimCR(s string) string {
	if n := len(s); n > 0 && s[n-1] == '\r' {
		return s[:n-1]
	}
	return s
}
