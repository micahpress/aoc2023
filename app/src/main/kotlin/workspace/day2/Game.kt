package workspace.day2

class Game(val id: Int, val maxCubeCounts: Map<String, Int>) {
    companion object Factory {
        fun fromString(input: String): Game {
            val splitInput: Iterator<String> = input.split(": ", "; ").iterator()
            val id: Int = splitInput.next().filter { it.isDigit() }.toInt()

            val maxCubeCounts: MutableMap<String, Int> = mutableMapOf()
            for( pull: String in splitInput ) {
                val splitPull: Iterator<String> = pull.split(", ").iterator()
                for ( colorCount: String in splitPull ) {
                    val splitColorCount: List<String> = colorCount.split(" ")
                    val count: Int = splitColorCount.get(0).toInt()
                    val color = splitColorCount.get(1)
                    if (count > maxCubeCounts.getOrDefault(color, 0)) {
                        maxCubeCounts.put(color, count)
                    }
                }
            }

            return Game(id, maxCubeCounts)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        val otherAsGame = other as Game
        return this.id == otherAsGame.id && this.maxCubeCounts.equals(otherAsGame.maxCubeCounts)
    }

    fun computePowerSet(): Int {
        return maxCubeCounts.values.reduce { acc, count -> acc * count}
    }
}
