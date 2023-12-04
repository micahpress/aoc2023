package workspace.day2

import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {
    @Test
    fun testParser() {
        val expected: Game = Game(12, mapOf("red" to 5, "green" to 3, "blue" to 10))
        assertEquals(expected, Game.fromString("Game 12: 4 blue, 3 green; 3 red, 3 green, 2 blue; 10 blue, 5 red"))
    }
}
