package workspace.day3

import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test() {
    @Test
    fun testRegexes() {
        val searchString = ".233...*..$4367#.%"

        var actualResults = NUMBER_REGEX.findAll(searchString)
        assertEquals(listOf(1, 11), actualResults.map { it.range.start }.toList())
        assertEquals(listOf(3, 14), actualResults.map { it.range.last }.toList())
        assertEquals(listOf("233", "4367"), actualResults.map { it.value }.toList())

        actualResults = SYMBOL_REGEX.findAll(searchString)
        assertEquals(listOf(7, 10, 15, 17), actualResults.map { it.range.start }.toList().also { println(it) })
        assertEquals(listOf("*", "$", "#", "%"), actualResults.map { it.value }.toList())
    }
}
