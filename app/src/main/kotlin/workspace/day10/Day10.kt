package workspace.day10

import java.io.File
import java.util.Queue
import java.util.Deque
import java.util.LinkedList
import workspace.util.getAdjacentCoords
import workspace.util.plus
import workspace.util.Coord
import workspace.util.times


class Day10Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines()
    }

    val charToCoordMod = mapOf(
        '|' to listOf(Pair(-1, 0), Pair(1, 0)),
        '-' to listOf(Pair(0, -1), Pair(0, 1)),
        'L' to listOf(Pair(-1, 0), Pair(0, 1)),
        'J' to listOf(Pair(-1, 0), Pair(0, -1)),
        '7' to listOf(Pair(0, -1), Pair(1, 0)),
        'F' to listOf(Pair(1, 0), Pair(0, 1)),
        'S' to getAdjacentCoords(Pair(0, 0))
    )

    fun getConnectedCoords(shape: Char, coords: Pair<Int, Int>): List<Coord> {
        return charToCoordMod.getValue(shape).map { coords.plus(it) }
    }

    fun part1(inputPath: String = "/workspace/inputs/day10.txt"): Int {
        val lines = loadInput(inputPath)
        var startCoords: Pair<Int, Int> = Pair(0, 0)
        for ((ind, line) in lines.withIndex()) {
            if (line.contains('S')) {
                startCoords = Pair(ind, line.indexOf('S'))
                break
            }
        }

        val adjacentCoords = getAdjacentCoords(startCoords)
        val visitedCoords = mutableSetOf<Coord>(startCoords)
        var coordsToVisit = adjacentCoords.filter { 0 <= it.first && it.first < lines.size && 0 <= it.second && it.second < lines[0].length }
            .filter { lines[it.first][it.second] in charToCoordMod.keys }
            .filter{ getConnectedCoords(lines[it.first][it.second], it).any { coords -> coords == startCoords } }
        var stepsAway = 0
        while (coordsToVisit.isNotEmpty()) {
            val nextCoords = coordsToVisit
                .map{ getConnectedCoords(lines[it.first][it.second], it) }.flatten()
                .filter { lines[it.first][it.second] in charToCoordMod.keys }
                .filter { 0 <= it.first && it.first < lines.size && 0 <= it.second && it.second < lines[0].length }
                .filterNot { it in visitedCoords }
            visitedCoords.addAll(coordsToVisit)
            coordsToVisit = nextCoords
            stepsAway++
        }

        return stepsAway
    }

    val charToScaledBlock = mapOf(
        '|' to listOf("*|*", "*|*", "*|*"),
        '-' to listOf("***", "---", "***"),
        'L' to listOf("*|*", "*L-", "***"),
        'J' to listOf("*|*", "-J*", "***"),
        '7' to listOf("***", "-7*", "*|*"),
        'F' to listOf("***", "*F-", "*|*"),
        '.' to listOf("***", "*.*", "***"),
        'S' to listOf("*S*", "SSS", "*S*")
    )

    fun scaleMap(mapLines: List<String>): List<String> {
        val scaledMap = MutableList(mapLines.size * 3) { "" }
        for ((row, line) in mapLines.withIndex()) {
            for (char in line) {
                val scaledBlock = charToScaledBlock.getValue(char)
                scaledBlock.forEachIndexed { ind, chars -> scaledMap[(row * 3) + ind] = scaledMap[(row * 3) + ind] + chars }
            }
        }
        return scaledMap
    }

    fun isInBounds(coord: Coord, rowLimit: Int, colLimit: Int): Boolean {
        return 0 <= coord.first && coord.first < rowLimit && 0 <= coord.second && coord.second < colLimit
    }

    fun findMainLoop(mapLines: List<String>, startCoords: Coord): Set<Coord> {
        val adjacentCoords = getAdjacentCoords(startCoords)
        var coordsToVisit = adjacentCoords.filter { isInBounds(it, mapLines.size, mapLines[0].length) }
            .filter { mapLines[it.first][it.second] in charToCoordMod.keys }
            .filter{ getConnectedCoords(mapLines[it.first][it.second], it).any { coords -> coords == startCoords } }
            .filter{ (getConnectedCoords(mapLines[it.first][it.second], it) - startCoords).any { coords -> isInBounds(coords, mapLines.size, mapLines[0].length) && mapLines[coords.first][coords.second] in charToCoordMod.keys } }
        val visitedCoords = mutableSetOf<Coord>(startCoords)
        val mainLoop = mutableSetOf<Coord>(startCoords)

        while (coordsToVisit.isNotEmpty()) {
            val nextCoords = coordsToVisit
                .map{ getConnectedCoords(mapLines[it.first][it.second], it) }.flatten()
                .filter { 0 <= it.first && it.first < mapLines.size && 0 <= it.second && it.second < mapLines[0].length }
                .filter { mapLines[it.first][it.second] in charToCoordMod.keys }
                .filterNot { it in visitedCoords }
            visitedCoords.addAll(coordsToVisit)
            mainLoop.addAll(coordsToVisit)
            coordsToVisit = nextCoords
        }

        return mainLoop
    }

    fun replaceNonMainLoopChars(mapLines: List<String>, mainLoop: Set<Coord>): List<String> {
        val replacedMap = MutableList(mapLines.size) { "" }
        for ((row, line) in mapLines.withIndex()) {
            line.forEachIndexed { col, c -> if (Pair(row, col) in mainLoop) replacedMap[row] = replacedMap[row] + c else replacedMap[row] = replacedMap[row] + "." }
        }
        return replacedMap
    }

    fun canFindEdge(coord: Coord, mapLines: List<String>, mainLoop: Set<Coord>, checkedTilesThatCanFindEdge: MutableSet<Coord>): Pair<Boolean, Int> {
        val visitedCoords = mutableSetOf(coord)
        val coordsToVisit = LinkedList<Coord>()
        val willVisit = mutableSetOf<Coord>(coord)
        coordsToVisit.add(coord)
        var visitedGroundTiles = 0
        while (coordsToVisit.isNotEmpty()) {
            val nextCoord = coordsToVisit.poll()
            if (nextCoord in checkedTilesThatCanFindEdge || !isInBounds(nextCoord, mapLines.size, mapLines[0].length)) {
                checkedTilesThatCanFindEdge.addAll(visitedCoords)
                return Pair(true, 0)
            }
            visitedCoords.add(nextCoord)
            if (mapLines[nextCoord.first][nextCoord.second] == '.') {
                visitedGroundTiles++
            }
            val toEnqueue = getAdjacentCoords(nextCoord).filter { it !in visitedCoords && it !in mainLoop && it !in willVisit }
            coordsToVisit.addAll(toEnqueue)
            willVisit.addAll(toEnqueue)
        }
        return Pair(false, visitedGroundTiles)
    }

    fun part2(inputPath: String = "/workspace/inputs/day10.txt"): Int {
        val lines = loadInput(inputPath)
        var startCoords: Pair<Int, Int> = Pair(0, 0)
        for ((ind, line) in lines.withIndex()) {
            if (line.contains('S')) {
                startCoords = Pair(ind, line.indexOf('S'))
                break
            }
        }

        val mainLoop = findMainLoop(lines, startCoords)
        val cleanMap = replaceNonMainLoopChars(lines, mainLoop)
        val scaledMap = scaleMap(cleanMap)
        val scaledMainLoop = findMainLoop(scaledMap, startCoords.times(Pair(3, 3)).plus(Pair(1, 1)))
        val memoizedWork = mutableSetOf<Coord>()
        for ((row, line) in cleanMap.withIndex()) {
            for ((col, tile) in line.withIndex()) {
                if (tile == '.') {
                    val canFindEdgeAndCount = canFindEdge(Pair(row, col).times(Pair(3, 3)).plus(Pair(1, 1)), scaledMap, scaledMainLoop, memoizedWork)
                    if (!canFindEdgeAndCount.first) {
                        return canFindEdgeAndCount.second
                    }
                }
            }
        }
        throw IllegalStateException()
    }
}
