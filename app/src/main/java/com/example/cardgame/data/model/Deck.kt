package com.example.cardgame.data.model
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Collections

// Represents a deck of cards.
class Deck {
    private val cards: MutableList<Card> = mutableListOf()

    init {
        // The deck is initialized with 52 standard playing cards.
        reset()
    }

    // Fills the deck with all 52 standard cards.
    fun reset() {
        cards.clear()
        for (suit in Suit.entries) {
            for (rank in Rank.entries) {
                cards.add(Card(suit, rank))
            }
        }
    }

    // Shuffles the cards in the deck randomly.
    fun shuffle() {
        Collections.shuffle(cards)
    }

    // Deals a single card from the top of the deck.
    // Returns null if the deck is empty.
    fun dealCard(): Card? {
        if (cards.isEmpty()) {
            return null
        }
        return cards.removeAt(0)
    }

    // Returns the number of cards remaining in the deck.
    fun cardsLeft(): Int {
        return cards.size
    }
}