package workspace.day4

import java.io.File

class Day4Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines()
    }
}