package workspace.day7

import java.io.File
import java.util.PriorityQueue


class Day7Solver() {
    fun loadInput(inputPath: String, jIsJoker: Boolean): List<Hand> {
        val lines = File(inputPath).readLines()
        val hands = mutableListOf<Hand>()
        for (line in lines) {
            val pieces = line.split(" ")
            hands.add(Hand(pieces.first(), pieces.last().toInt(), jIsJoker))
        }
        return hands
    }

    fun part1(inputPath: String = "/workspace/inputs/day7.txt"): Long {
        val pq = PriorityQueue(loadInput(inputPath, false))
        var winnings = 0L
        while (pq.isNotEmpty()) {
            winnings += pq.size * pq.poll().bid
        }
        return winnings
    }

    fun part2(inputPath: String = "/workspace/inputs/day7.txt"): Long {
        val pq = PriorityQueue(loadInput(inputPath, true))
        var winnings = 0L
        while (pq.isNotEmpty()) {
            winnings += pq.size * pq.poll().bid
        }
        return winnings
    }
}
