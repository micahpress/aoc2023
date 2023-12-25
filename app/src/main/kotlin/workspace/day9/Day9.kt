package workspace.day9

import java.io.File


class Day9Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines()
    }

    fun convertToLists(inputLines: List<String>): List<MutableList<Int>> {
        return inputLines.map { it.split(" ").map { it.toInt() }.toMutableList() }
    }

    fun iterativelySubtract(sequence: MutableList<Int>): Unit {
        var lastIndex = sequence.size - 1
        var diffIsZero = false
        while (!diffIsZero && lastIndex > 0) {
            for (i in 0..<lastIndex) {
                sequence[i] = sequence[i + 1] - sequence[i]
            }
            lastIndex--
            diffIsZero = sequence.subList(0, lastIndex).all { it == 0 }
        }
    }

    fun part1(inputPath: String = "/workspace/inputs/day9.txt"): Int {
        val sequences = convertToLists(loadInput(inputPath))
        sequences.forEach { iterativelySubtract(it) }
        val nextTerms = sequences.map { it.sum() }
        var total = 0
        for (term in nextTerms) {
            total += term
        }
        return total
    }

    fun part2(inputPath: String = "/workspace/inputs/day9.txt"): Int {
        val sequences = convertToLists(loadInput(inputPath)).map { it.reversed().toMutableList() }
        sequences.forEach { iterativelySubtract(it) }
        val nextTerms = sequences.map { it.sum() }
        var total = 0
        for (term in nextTerms) {
            total += term
        }
        return total
    }
}
