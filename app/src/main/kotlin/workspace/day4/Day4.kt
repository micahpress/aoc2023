package workspace.day4

import java.io.File

class Day4Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines()
    }

    fun part1(inputPath: String = "/workspace/inputs/day4.txt"): Int {
        return loadInput(inputPath).map { Card.fromString(it) }.fold(0) { acc, card -> acc + card.score() }
    }

    fun part2(inputPath: String = "/workspace/inputs/day4.txt"): Int {
        val cards: List<Card> = loadInput(inputPath).map { Card.fromString(it) }
        val cardCounts: Array<Int> = Array(cards.size, { 1 })

        for (card in cards) {
            val wins = card.countWins()
            for (id in card.id + 1..card.id + wins) {
                cardCounts[id - 1] += cardCounts[card.id - 1]
            }
        }

        return cardCounts.sum()
    }
}