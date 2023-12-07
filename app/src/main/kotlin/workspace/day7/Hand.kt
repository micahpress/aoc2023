package workspace.day7

import workspace.day7.CARD_RANKING
import kotlin.collections.sortedMapOf


class Hand(val cards: String, val bid: Int, val jIsJoker: Boolean) : Comparable<Hand> {
    val kind: HandKind

    init {
        kind = classifyHand(cards, jIsJoker)
    }

    override fun compareTo(other: Hand): Int {
        val ranking = if (jIsJoker) JOKER_CARD_RANKING else CARD_RANKING
        if (cards.equals(other.cards)) {
            return 0
        } else if (kind > other.kind) {
            return 1
        } else if (kind < other.kind) {
            return -1
        } else {
            for ((thisCard, otherCard) in cards.zip(other.cards)) {
                val compResult = ranking.getValue(thisCard).compareTo(ranking.getValue(otherCard))
                if (compResult != 0) return compResult
            }
            return 0
        }
    }
}


val CARD_RANKING = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A').zip(12 downTo 0).toMap()
val JOKER_CARD_RANKING = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A').zip(12 downTo 0).toMap()


fun classifyHand(cards: String, jIsJoker: Boolean): HandKind {
    val sortedCards = if (jIsJoker) generateBestHand(cards) else cards.toCharArray().sorted().joinToString("")
    for (kind in HandKind.values()) {
        if (kind.regex().matches(sortedCards)) return kind
    }
    return HandKind.HIGH
}


fun generateBestHand(cards: String): String {
    if ('J' in cards && cards != "JJJJJ") {
        val cardOccurences = mutableMapOf<Char, Int>()
        for (card in cards) {
            cardOccurences.put(card, cardOccurences.getOrDefault(card, 0) + 1)
        }
        val numJokers = cardOccurences.getValue('J')
        cardOccurences.remove('J')
        val sortedCardOccurences = cardOccurences.entries.sortedWith(compareBy { -it.value })
        cardOccurences.put(sortedCardOccurences.first().key, sortedCardOccurences.first().value + numJokers)
        val toReturn = cardOccurences.entries.fold("") { str, p -> str + p.key.toString().repeat(p.value) }
        return toReturn
    } else {
        return cards.toCharArray().sorted().joinToString("")
    }
}


enum class HandKind {
    FIVE {
        override fun regex() = Regex("(.)\\1\\1\\1\\1")
    },
    FOUR {
        override fun regex() = Regex(".*(.)\\1\\1\\1.*")
    },
    FULL_HOUSE {
        override fun regex() = Regex("(.)\\1(.)\\2\\2|(.)\\3\\3(.)\\4")
    },
    THREE {
        override fun regex() = Regex(".*(.)\\1\\1.*")
    },
    TWO_PAIR {
        override fun regex() = Regex(".*(.)\\1.*(.)\\2.*")
    },
    PAIR {
        override fun regex() = Regex(".*(.)\\1.*")
    },
    HIGH {
        override fun regex() = Regex(".+")
    };

    abstract fun regex(): Regex
}
