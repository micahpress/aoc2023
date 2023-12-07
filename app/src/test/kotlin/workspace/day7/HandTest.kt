package workspace.day7

import kotlin.test.Test
import kotlin.test.assertEquals


class HandTest() {
    @Test
    fun testRegexes() {
        assertEquals(HandKind.FIVE, classifyHand("AAAAA", false))
        assertEquals(HandKind.FOUR, classifyHand("AA2AA", false))
        assertEquals(HandKind.FULL_HOUSE, classifyHand("AA22A", false))
        assertEquals(HandKind.THREE, classifyHand("AA23A", false))
        assertEquals(HandKind.TWO_PAIR, classifyHand("AA233", false))
        assertEquals(HandKind.PAIR, classifyHand("AA234", false))
        assertEquals(HandKind.HIGH, classifyHand("A2345", false))
    }

    @Test
    fun testHandCompare() {
        val hand1 = Hand("AA2A2", 0, false)
        val hand2 = Hand("22222", 0, false)
        assertEquals(1, hand1.compareTo(hand2))
        val hand3 = Hand("2AA2A", 0, false)
        assertEquals(-1, hand1.compareTo(hand3))
        assertEquals(0, hand1.compareTo(hand1))
    }

    @Test
    fun testGenerateBestHand() {
        assertEquals("JJJJJ", generateBestHand("JJJJJ"))
    }
}
