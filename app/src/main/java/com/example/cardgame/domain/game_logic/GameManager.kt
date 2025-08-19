import java.util.Collections
import com.example.cardgame.data.model.*
// Manages the overall game state, players, and game flow for the game Judgement.
class GameManager {
    private val deck = Deck()
    private val players: MutableList<Player> = mutableListOf()
    private lateinit var trumpSuit: Suit
    private var currentTrick: MutableList<Card> = mutableListOf()
    private val trickWinners: MutableMap<Player, Int> = mutableMapOf()
    private val playerBids: MutableMap<Player, Int> = mutableMapOf()
    private var cardsInRound = 0
    private var currentPlayerIndex = 0

    // Adds a player to the game.
    fun addPlayer(player: Player) {
        players.add(player)
        trickWinners[player] = 0
    }

    // Starts a new game session.
    fun startNewGame(startingCards: Int, maxCards: Int) {
        println("Starting a new game of Judgement with ${players.size} players.")

        // Loop through rounds from startingCards up to maxCards and back down to startingCards
        for (i in startingCards..maxCards) {
            playRound(i)
        }
        for (i in maxCards - 1 downTo startingCards) {
            playRound(i)
        }

        // Final scoring and winner declaration
        determineFinalWinner()
    }

    private fun playRound(numCards: Int) {
        cardsInRound = numCards
        println("\n--- Starting Round with $cardsInRound cards ---")

        // Reset and deal
        resetRound()
        dealCards()

        // Determine trump suit
        val trumpCard = deck.dealCard()
        trumpSuit = trumpCard?.suit ?: run {
            println("No trump suit this round.")
            // Set to a suit that doesn't exist to represent no trump
            Suit.values().last()
        }
        if (trumpCard != null) {
            println("The trump suit for this round is: ${trumpSuit.name}")
        }

        // Bidding
        getBids()

        // Play all tricks
        playTricks()

        // Score the round
        scoreRound()
    }

    private fun resetRound() {
        deck.reset()
        deck.shuffle()
        players.forEach {
            it.clearHand()
            trickWinners[it] = 0
        }
        playerBids.clear()
    }

    private fun dealCards() {
        for (i in 0 until cardsInRound) {
            for (player in players) {
                deck.dealCard()?.let { player.receiveCard(it) }
            }
        }
        println("$cardsInRound cards dealt to each player.")
    }

    private fun getBids() {
        val lastPlayerIndex = (players.size - 1)
        val sumOfBids = playerBids.values.sum()

        println("It's time to bid!")

        // Bidding logic, simulating user input
        for (i in 0 until players.size) {
            val player = players[i]

            // This is where you would get the actual user's bid from the UI.
            // For now, we'll use a random bid for demonstration.
            val bid = (0..cardsInRound).random()

            // Apply the "Oh Hell!" rule for the last player.
            if (i == lastPlayerIndex) {
                if (sumOfBids + bid == cardsInRound) {
                    println("${player.name} cannot bid $bid. Bidding again...")
                    // Rerun a random bid until it's valid.
                    var newBid = bid
                    while (sumOfBids + newBid == cardsInRound) {
                        newBid = (0..cardsInRound).random()
                    }
                    playerBids[player] = newBid
                    println("${player.name} bids $newBid.")
                } else {
                    playerBids[player] = bid
                    println("${player.name} bids $bid.")
                }
            } else {
                playerBids[player] = bid
                println("${player.name} bids $bid.")
            }
        }
        println("All bids are in. Total bids: ${playerBids.values.sum()}")
    }

    private fun playTricks() {
        var leaderIndex = (currentPlayerIndex + 1) % players.size // Player to dealer's left leads first

        repeat(cardsInRound) { trickNumber ->
            println("\n--- Trick ${trickNumber + 1} ---")

            val leader = players[leaderIndex]
            val leadingCard = leader.hand[0] // Assuming the player chooses their first card
            leader.playCard(leadingCard)
            currentTrick.add(leadingCard)
            println("${leader.name} leads with $leadingCard")

            // Each player plays a card
            for (i in 1 until players.size) {
                val playerIndex = (leaderIndex + i) % players.size
                val player = players[playerIndex]

                // Simplified card playing logic:
                // Find a card to follow suit.
                val cardToPlay = player.hand.find { it.suit == leadingCard.suit }
                    ?: player.hand.find { it.suit == trumpSuit } // Or play a trump
                    ?: player.hand[0] // Otherwise, play any card

                player.playCard(cardToPlay)
                currentTrick.add(cardToPlay)
                println("${player.name} plays $cardToPlay")
            }

            // Determine the winner of the trick
            val trickWinner = determineTrickWinner(leaderIndex)
            trickWinners[trickWinner] = (trickWinners[trickWinner] ?: 0) + 1
            println("${trickWinner.name} wins the trick!")

            currentTrick.clear()
            leaderIndex = players.indexOf(trickWinner) // Winner of the trick leads the next
        }
    }

    private fun determineTrickWinner(leaderIndex: Int): Player {
        var winningCard = currentTrick[0]
        var winnerIndex = leaderIndex

        // Find the highest trump card played, if any
        val trumpsInTrick = currentTrick.filter { it.suit == trumpSuit }
        if (trumpsInTrick.isNotEmpty()) {
            val highestTrump = trumpsInTrick.maxByOrNull { it.rank.value }!!
            val winningCardIndex = currentTrick.indexOf(highestTrump)
            return players[(leaderIndex + winningCardIndex) % players.size]
        }

        // Otherwise, find the highest card of the leading suit
        val leadingSuitCards = currentTrick.filter { it.suit == currentTrick[0].suit }
        val highestLeadingCard = leadingSuitCards.maxByOrNull { it.rank.value }!!
        val winningCardIndex = currentTrick.indexOf(highestLeadingCard)

        return players[(leaderIndex + winningCardIndex) % players.size]
    }

    private fun scoreRound() {
        println("\n--- Round Scoring ---")
        players.forEach { player ->
            val bid = playerBids[player] ?: 0
            val tricksWon = trickWinners[player] ?: 0

            if (bid == tricksWon) {
                val score = 10 + tricksWon
                player.score += score
                println("${player.name} bid $bid and won $tricksWon tricks. Correct! Scores $score points.")
            } else {
                println("${player.name} bid $bid but won $tricksWon tricks. Incorrect. Scores 0 points.")
            }
        }
    }

    private fun determineFinalWinner() {
        println("\n--- Final Scores ---")
        val winner = players.maxByOrNull { it.score }
        players.sortedByDescending { it.score }.forEach { player ->
            println("${player.name}: ${player.score} points")
        }
        println("\nAnd the winner is... ${winner?.name} with ${winner?.score} points!")
    }
}