package workspace.day4

data class Card(val id: Int, val winningNumbers: Set<Int>, val presentNumbers: Set<Int>) {
    companion object Factory {
        fun fromString(arg: String): Card {
            val parts = arg.split(":", "|").iterator()
            val id = parts.next().split(" ").last().toInt()
            val winningNumbers = parts.next().split(" ").filter { it != "" }.map { it.toInt() }.toSet()
            val presentNumbers= parts.next().split(" ").filter { it != "" }.map { it.toInt() }.toSet()
            return Card(id, winningNumbers, presentNumbers)
        }
    }

    fun countWins(): Int {
        return (winningNumbers intersect presentNumbers).size
    }

    fun score(): Int {
        val wins = countWins()
        return if (wins > 0) (1 shl (wins - 1)) else 0
    }
}