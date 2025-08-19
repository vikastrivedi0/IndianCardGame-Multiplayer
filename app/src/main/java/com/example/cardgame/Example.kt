package com.example.cardgame

import GameManager
import com.example.cardgame.data.model.Player

class Example {

    // Example usage in your main activity or a test function
    fun main() {
        println("--- Starting CLI Test of Judgement Game Logic ---")

        val gameManager = GameManager()

        // 1. Create players and add them to the game manager
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val player3 = Player("Charlie")

        gameManager.addPlayer(player1)
        gameManager.addPlayer(player2)
        gameManager.addPlayer(player3)

        // 2. Start the game with a specified number of rounds
        // This will simulate the entire game from dealing to scoring.
        gameManager.startNewGame(startingCards = 1, maxCards = 3)

        // 3. You can add more specific tests here if needed
        // For example, to test a single round's logic:
        // gameManager.playRound(3)

        println("\n--- CLI Test Complete ---")
    }
}