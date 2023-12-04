package workspace.day1

import java.io.File
import java.util.PriorityQueue

class Day1Solver {
    fun loadInput(inputPath: String = "/workspace/inputs/day1.txt"): List<String> {
        return File(inputPath).readLines()
    }

    fun filterToDigits(arg: String): String {
        return arg.filter { chr -> chr.isDigit() };
    }

    fun convertFirstLastDigitsToNum(arg: String): Int {
        return (arg.first().toString() + arg.last()).toInt()
    }

    fun part1(inputPath: String): String {
        return loadInput(inputPath).map { convertFirstLastDigitsToNum(filterToDigits(it)) }.sum().toString()
    }

    fun part1(): String {
        return loadInput().map { convertFirstLastDigitsToNum(filterToDigits(it)) }.sum().toString()
    }

    val wordsToDigits: Map<String, String> = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    fun replaceWordsWithDigits(arg: String): String {
        // create min pq of <digit, starting index>
        val pq: PriorityQueue<Pair<String, Int>> = PriorityQueue(compareBy({it.second}))
        for( entry in wordsToDigits.entries ) {
            val wordRegex: Regex = Regex(entry.key)
            val wordMatches: Sequence<MatchResult> = wordRegex.findAll(arg)
            val digitRegex: Regex = Regex(entry.value)
            val digitMatches: Sequence<MatchResult> = digitRegex.findAll(arg)
            for( match in (wordMatches + digitMatches) ) {
                pq.add(Pair(entry.value, match.range.start))
            }
        }

        // drain pq to get back full numstring
        var outputString = ""
        while (!pq.isEmpty()) {
            outputString += pq.poll().first
        }

        return outputString
    }

    fun part2(inputPath: String): String {
        return loadInput(inputPath).map { convertFirstLastDigitsToNum(filterToDigits(replaceWordsWithDigits(it))) }.sum().toString()
    }

    fun part2(): String {
        return loadInput().map { convertFirstLastDigitsToNum(filterToDigits(replaceWordsWithDigits(it))) }.sum().toString()
    }
}
