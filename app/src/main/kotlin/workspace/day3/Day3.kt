package workspace.day3

import java.io.File


val NUMBER_REGEX = Regex("\\d+")
val SYMBOL_REGEX = Regex("[^\\.^\\d]")
val GEAR_REGEX = Regex("\\*")

class Day3Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines()
    }

    fun findSymbols(arg: String): List<Int> {
        return SYMBOL_REGEX.findAll(arg).map { it.range.start }.toList()
    }

    fun findNumbers(arg: String): List<Pair<String, Int>> {
        return NUMBER_REGEX.findAll(arg).map { Pair(it.value, it.range.start) }.toList()
    }

    fun findGears(arg: String): List<Pair<String, Int>> {
        return GEAR_REGEX.findAll(arg).map { Pair(it.value, it.range.start) }.toList()
    }

    fun isNearSymbol(numTriple: Triple<String, Int, Int>, symbols: Set<Pair<Int, Int>>): Boolean {
        return computeAdjacentCoords(numTriple).filter { it in symbols }.any()
    }

    fun part1(inputPath: String = "/workspace/inputs/day3.txt"): Int {
        val symbolLocations: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val numbers: MutableList<Triple<String, Int, Int>> = mutableListOf()
        loadInput(inputPath).forEachIndexed { row, line ->
            symbolLocations.addAll( findSymbols(line).map({ col -> Pair(row, col) }))
            numbers.addAll( findNumbers(line).map({ Triple(it.first, row, it.second) }))
        }
        return numbers.filter { isNearSymbol(it, symbolLocations) }.fold(0, { acc, numTriple -> acc + numTriple.first.toInt() })
    }

    fun computeCoords(objectTriple: Triple<String, Int, Int>): Map<Pair<Int, Int>, String> {
        val (num, row, col) = objectTriple
        return (col..col + num.length - 1).map { Pair(row, it) }.map { Pair(it, num) }.toMap()
    }

    fun computeAdjacentCoords(objectTriple: Triple<String, Int, Int>): List<Pair<Int, Int>> {
        val adjacentCoords = mutableListOf<Pair<Int, Int>>()
        for (row in (objectTriple.second - 1)..(objectTriple.second + 1)) {
            for (col in (objectTriple.third - 1)..(objectTriple.third + objectTriple.first.length)) {
                if (!(row == objectTriple.second && col in objectTriple.third..(objectTriple.third + objectTriple.first.length - 1))) {
                    adjacentCoords.add(Pair(row, col))
                }
            }
        }
        return adjacentCoords
    }

    fun part2(inputPath: String = "/workspace/inputs/day3.txt"): Int {
        val gearLocations: MutableList<Triple<String, Int, Int>> = mutableListOf()
        val numbers: MutableList<Triple<String, Int, Int>> = mutableListOf()
        loadInput(inputPath).forEachIndexed { row, line ->
            gearLocations.addAll( findGears(line).map({ Triple(it.first, row, it.second) }))
            numbers.addAll( findNumbers(line).map({ Triple(it.first, row, it.second) }))
        }
        val numberLocationMap = numbers.fold(mapOf<Pair<Int, Int>, String>()) { acc, num -> acc.plus(computeCoords(num)) }
        return gearLocations.map { gear -> computeAdjacentCoords(gear).filter { it in numberLocationMap }.map { numberLocationMap.getValue(it).toInt() }.toSet() }.filter { it.size == 2 }.map { it.reduce { acc, num -> acc * num } }.sum()
    }
}
