package workspace.day4

import kotlin.test.Test
import kotlin.test.assertEquals

class CardTest() {
    @Test
    fun factoryTest() {
        val expected = Card(13, setOf(3, 56, 34, 190), setOf(45, 3, 34, 231, 67))
        assertEquals(expected, Card.fromString("Card 13:  3 56 190 34 | 45 34  3 231 67"))
    }
}