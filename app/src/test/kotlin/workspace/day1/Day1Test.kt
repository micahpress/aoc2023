package workspace.day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    @Test
    fun testWordReplacement() {
        assertEquals("82", Day1Solver().replaceWordsWithDigits("eightwo"))
        assertEquals("812", Day1Solver().replaceWordsWithDigits("eight1two"))
        assertEquals("1812", Day1Solver().replaceWordsWithDigits("1eight1two"))
        assertEquals("18121", Day1Solver().replaceWordsWithDigits("1eight1two1"))
    }
}