package domain

class Game {

    private var playerOneScore = 0
    private var playerTwoScore = 0

    fun playerOneScored() {
        playerOneScore++
    }

    fun playerTwoScored() {
        playerTwoScore++
    }

    fun printScore(): String {
        fromAdvantageToDeuce()
        if (didPlayerOneWin()) return "player one wins"
        if (didPlayerTwoWin()) return "player two wins"
        if (playerOneScore == 4) return "advantage player one"
        if (playerTwoScore == 4) return "advantage player two"
        return if (playerOneScore == 3 && playerTwoScore == 3) "deuce"
        else "${printPlayerScore(playerOneScore)}-${printPlayerScore(playerTwoScore)}"
    }

    private fun didPlayerTwoWin(): Boolean = playerTwoScore == 5 || (playerTwoScore == 4 && playerOneScore < 3)

    private fun didPlayerOneWin(): Boolean = playerOneScore == 5 || (playerOneScore == 4 && playerTwoScore < 3)

    private fun fromAdvantageToDeuce() {
        if (playerOneScore == 4 && playerTwoScore == 4) {
            playerOneScore--
            playerTwoScore--
        }
    }

    private fun printPlayerScore(score: Int): String {
        return ScorePoints.values()[score].value
    }
}
