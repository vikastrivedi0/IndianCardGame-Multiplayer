package com.example.cardgame.data.model

// Represents a single player in the game.
data class Player(val name: String, var score: Int = 0) {
    val hand: MutableList<Card> = mutableListOf()

    // Adds a card to the player's hand.
    fun receiveCard(card: Card) {
        hand.add(card)
    }

    // Removes a card from the player's hand.
    fun playCard(card: Card): Boolean {
        return hand.remove(card)
    }

    // Clears the player's hand of all cards.
    fun clearHand() {
        hand.clear()
    }
}