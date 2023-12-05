package workspace.day5

import java.io.File
import workspace.day3.NUMBER_REGEX
import workspace.util.tail

class Day5Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readText().split("\n\n")
    }

    fun parseSeeds(arg: String): List<Long> {
        return NUMBER_REGEX.findAll(arg).map { it.value.toLong() }.toList()
    }

    fun parseTransformer(block: String): Map<LongRange, Long> {
        val ranges = block.split("\n").tail()
        val transformerMap = mutableMapOf<LongRange, Long>()
        for (range in ranges) {
            val specs = range.split(" ").map { it.toLong() }
            val sourceStart = specs.get(1)
            val destStart = specs.get(0)
            val len = specs.get(2)
            val transformation = destStart - sourceStart
            transformerMap.put(LongRange(sourceStart, sourceStart + len - 1), transformation)
        }
        return transformerMap
    }

    fun transformSeed(seed: Long, transformers: List<Map<LongRange, Long>>): Long {
        var transformed = seed
        for (transformer in transformers) {
            for ((range, transformation) in transformer) {
                if (transformed in range) {
                    transformed += transformation
                    break
                }
            }
        }
        return transformed
    }

    fun part1(inputPath: String = "/workspace/inputs/day5.txt"): Long {
        val input = loadInput(inputPath)
        val seeds = parseSeeds(input.first())
        val transformations = input.tail().map { parseTransformer(it) }
        var minFinal = Long.MAX_VALUE
        for (seed in seeds) {
            var transformed = transformSeed(seed, transformations)
            minFinal = if (transformed < minFinal) transformed else minFinal
        }
        return minFinal
    }

    fun parseSeedRanges(arg: String): List<LongRange> {
        val seedIndices = NUMBER_REGEX.findAll(arg).map { it.value.toLong() }.toList()
        val ranges = mutableListOf<LongRange>()
        for (i in 0..seedIndices.size - 1 step 2) {
            val start = seedIndices.get(i)
            val end = seedIndices.get(i) + seedIndices.get(i + 1) - 1
            ranges.add(LongRange(start, end))
        }
        return ranges
    }

    fun part2(inputPath: String = "/workspace/inputs/day5.txt"): Long {
        val input = loadInput(inputPath)
        val seedRanges = parseSeedRanges(input.first())
        val transformations = input.tail().map { parseTransformer(it) }
        var minFinal = Long.MAX_VALUE
        var workDone = 0L
        for (range in seedRanges) {
            for (seed in range) {
                var transformed = transformSeed(seed, transformations)
                minFinal = if (transformed < minFinal) transformed else minFinal
                workDone++
            }
        }
        return minFinal
    }
}
