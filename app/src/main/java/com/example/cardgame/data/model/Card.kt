package com.example.cardgame.data.model

data class Card(val suit: Suit, val rank: Rank) {
    override fun toString(): String {
        return "${rank.name} of ${suit.name}"
    }
}
