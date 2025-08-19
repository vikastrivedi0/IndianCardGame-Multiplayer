package com.example.cardgame.data.model

// Represents the four suits in a standard deck of cards.
enum class Suit {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES
}

// Represents the ranks of the cards, with a numerical value.
enum class Rank(val value: Int) {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14)
}