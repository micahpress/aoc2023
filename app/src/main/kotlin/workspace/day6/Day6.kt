package workspace.day6

import java.io.File
import workspace.day3.NUMBER_REGEX
import workspace.util.tail
import workspace.util.max
import workspace.util.min

class Day6Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines()
    }

    fun parseInput(input: List<String>): List<Pair<Double, Double>> {
        val times = NUMBER_REGEX.findAll(input.first()).map { it.value.toDouble() }
        val distances = NUMBER_REGEX.findAll(input.tail().first()).map { it.value.toDouble() }
        return times.zip(distances).toList()
    }

    fun findQuadraticRoots(a: Double, b: Double, c: Double): Pair<Double, Double> {
        return Pair(
            (-b + Math.sqrt((b * b) - (4.0 * a * c))) / (2.0 * a),
            (-b - Math.sqrt((b * b) - (4.0 * a * c))) / (2.0 * a)
        )
    }

    fun roundInwards(arg: Pair<Double, Double>): Pair<Double, Double> {
        return Pair(Math.floor(arg.first + 1), Math.ceil(arg.second - 1))
    }

    fun enforceMovement(arg: Pair<Double, Double>, limit: Double): Pair<Double, Double> {
        return Pair(max(arg.first, 1.toDouble()), min(arg.second, limit - 1))
    }

    fun calcMultipliedMarginOfError(races: List<Pair<Double, Double>>): Int {
        var multipliedMarginOfError = 1
        for (race in races) {
            val limits = findQuadraticRoots(-1.0, race.first, -race.second)
            val realisticLimits = enforceMovement(roundInwards(limits), race.first.toDouble())
            multipliedMarginOfError *= (realisticLimits.second - realisticLimits.first + 1).toInt()
        }
        return multipliedMarginOfError
    }

    fun part1(inputPath: String = "/workspace/inputs/day6.txt"): Int {
        val races = parseInput(loadInput(inputPath))
        return calcMultipliedMarginOfError(races)
    }

    fun parseRace(input: List<String>): List<Pair<Double, Double>> {
        val time = input.first().filter { it.isDigit() }.toDouble()
        val distance = input.tail().first().filter { it.isDigit() }.toDouble()
        return listOf(Pair(time, distance))
    }

    fun part2(inputPath: String = "/workspace/inputs/day6.txt"): Int {
        return calcMultipliedMarginOfError(parseRace(loadInput(inputPath)))
    }
}
