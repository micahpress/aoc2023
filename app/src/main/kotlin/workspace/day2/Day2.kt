package workspace.day2

import java.io.File

class Day2Solver {
    fun loadInput(inputPath: String = "/workspace/inputs/day2.txt"): List<String> {
        return File(inputPath).readLines()
    }

    fun convertToGame(arg: String): Game {
        return Game.fromString(arg)
    }

    fun isPossiblePart1(cubeCounts: Map<String, Int>): Boolean {
        return (
            cubeCounts.getOrDefault("red", 0) <= 12
            && cubeCounts.getOrDefault("green", 0) <= 13
            && cubeCounts.getOrDefault("blue", 0) <= 14
        )
    }

    fun part1(inputPath: String): Int {
        return loadInput(inputPath).map { convertToGame(it) }.filter { isPossiblePart1(it.maxCubeCounts) }.fold(0, { acc, game -> acc + game.id })
    }

    fun part1(): Int {
        return loadInput().map { convertToGame(it) }.filter { isPossiblePart1(it.maxCubeCounts) }.fold(0, { acc, game -> acc + game.id })
    }

    fun part2(inputPath: String): Int {
        return loadInput(inputPath).map { convertToGame(it).computePowerSet() }.sum()
    }

    fun part2(): Int {
        return loadInput().map { convertToGame(it).computePowerSet() }.sum()
    }
}
