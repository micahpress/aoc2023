package workspace.util

import kotlin.test.Test
import kotlin.test.assertEquals

class UtilTest() {
    @Test
    fun testMergeButTake() {
        fun pickBigger(v1: Int, v2: Int): Int {
            return listOf(v1, v2).max()
        }
        fun pickSmaller(v1: Int, v2: Int): Int {
            return listOf(v1, v2).min()
        }

        val map1 = mapOf("red" to 2, "blue" to 5)
        val map2 = mapOf("green" to 7, "blue" to 6)
        val mergedBiggerMap = map1.mergeButTake(map2) { _, v1, v2 -> pickBigger(v1, v2) }
        assertEquals(mapOf("red" to 2, "green" to 7, "blue" to 6), mergedBiggerMap, "$mergedBiggerMap")
        val mergedSmallerMap = map1.mergeButTake(map2) { _, v1, v2 -> pickSmaller(v1, v2) }
        assertEquals(mapOf("red" to 2, "green" to 7, "blue" to 5), mergedSmallerMap, "$mergedSmallerMap")
    }

    @Test
    fun testGetCoords() {
        val coord = Pair(1, 1)
        assertEquals(setOf(Pair(0, 1), Pair(1, 0), Pair(1, 2), Pair(2, 1)), getAdjacentCoords(coord))
        assertEquals(setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0),
                           Pair(0, 2), Pair(1, 2), Pair(2, 2),
                           Pair(0, 1), Pair(2, 1)), getSurroundingCoords(coord))
    }
}
