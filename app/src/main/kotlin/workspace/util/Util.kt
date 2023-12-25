package workspace.util

fun List<String>.tail(): List<String> {
    when (this.size) {
        0 -> throw IndexOutOfBoundsException()
        else -> return this.subList(1, this.size)
    }
}

fun max(a: Double, b: Double): Double {
    return if (a > b) a else b
}

fun min(a: Double, b: Double): Double {
    return if (a < b) a else b
}

fun factor(numArg: Int): Map<Int, Int> {
    var num = numArg
    val factorsToOccurrences = mutableMapOf<Int, Int>().withDefault { 0 }
    var candidate = 2
    while (num > 1 && candidate <= Math.sqrt(num.toDouble())) {
        if (num % candidate == 0) {
            factorsToOccurrences.put(candidate, factorsToOccurrences.getValue(candidate) + 1)
            num /= candidate
            candidate = 2
        } else {
            candidate += 1
        }
    }
    if (num > 1) {
        factorsToOccurrences.put(num, factorsToOccurrences.getValue(num) + 1)
    }
    return factorsToOccurrences
}

fun <K, V> Map<K, V>.mergeButTake(other: Map<K, V>, takeFunction: (key: K, v1: V, v2: V) -> V): Map<K, V> {
    val mergedMap = mutableMapOf<K, V>()
    val uniqueKeys = (this.keys - other.keys) union (other.keys - this.keys)
    val sharedKeys = this.keys intersect other.keys
    for ((key, value) in this) {
        if (key in uniqueKeys) {
            mergedMap.put(key, value)
        } else if (key in sharedKeys) {
            val otherValue = other.getValue(key)
            mergedMap.put(key, takeFunction.invoke(key, value, otherValue))
        }
    }
    for ((key, value) in other) {
        if (key in uniqueKeys) {
            mergedMap.put(key, value)
        }
    }
    return mergedMap.toMap()
}

fun getAdjacentCoords(coords: Pair<Int, Int>): Set<Pair<Int, Int>> {
    return setOf(
        Pair(coords.first - 1, coords.second),
        Pair(coords.first + 1, coords.second),
        Pair(coords.first, coords.second - 1),
        Pair(coords.first, coords.second + 1)
    )
}

fun getSurroundingCoords(coords: Pair<Int, Int>): Set<Pair<Int, Int>> {
    return setOf(
        Pair(coords.first - 1, coords.second - 1),
        Pair(coords.first - 1, coords.second),
        Pair(coords.first - 1, coords.second + 1),
        Pair(coords.first, coords.second - 1),
        Pair(coords.first, coords.second + 1),
        Pair(coords.first + 1, coords.second - 1),
        Pair(coords.first + 1, coords.second),
        Pair(coords.first + 1, coords.second + 1)
    )
}

fun Coord.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

fun Coord.times(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first * other.first, this.second * other.second)
}

typealias Coord = Pair<Int, Int>
