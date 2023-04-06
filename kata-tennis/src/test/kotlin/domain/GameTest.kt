package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameTest {

    lateinit var game: Game
    
    @BeforeEach
    fun beforeEach() {
        game = Game()
    }

    @Test
    fun `should display both players initial score`() {
        assertThat(game.printScore()).isEqualTo("love-love")
    }

    @Test
    fun `should display 15 - 0 when player one scored`() {
        game.playerOneScored()

        assertThat(game.printScore()).isEqualTo("15-love")
    }

    @Test
    fun `should display 0 - 15 when player two scored`() {
        game.playerTwoScored()

        assertThat(game.printScore()).isEqualTo("love-15")
    }

    @Test
    fun `should display 15 - 15 when both scored`() {
        game.playerTwoScored()
        game.playerOneScored()

        assertThat(game.printScore()).isEqualTo("15-15")
    }

    @Test
    fun `should display 30 - 15 when player one scored two times and player two once`() {
        game.playerTwoScored()
        game.playerOneScored()
        game.playerOneScored()

        assertThat(game.printScore()).isEqualTo("30-15")
    }

    @Test
    fun `should display 40 - 0 when player one scored three times and player two zero`() {
        playerOneScoreThreeTimes()
        assertThat(game.printScore()).isEqualTo("40-love")
    }

    @Test
    fun `should display 'deuce' when player one and two made an equality`() {
        deuce()
        assertThat(game.printScore()).isEqualTo("deuce")
    }

    @Test
    fun `should display 'advantage player one' when player one have score after deuce`() {
        deuce()

        game.playerOneScored()

        assertThat(game.printScore()).isEqualTo("advantage player one")
    }

    @Test
    fun `should display 'advantage player two' when player two have score after deuce`() {
        deuce()

        game.playerTwoScored()

        assertThat(game.printScore()).isEqualTo("advantage player two")
    }

    @Test
    fun `should display 'player one wins' when player one score while he has advantage`() {
        playerOneHasAdvantage()
        game.playerOneScored()

        assertThat(game.printScore()).isEqualTo("player one wins")
    }

    @Test
    fun `should display 'player two wins' when player two score while he has advantage`() {
        playerTwoHasAdvantage()
        game.playerTwoScored()

        assertThat(game.printScore()).isEqualTo("player two wins")
    }

    @Test
    fun `should display 'deuce' when player one has advantage and player two scored`() {
        playerOneHasAdvantage()
        game.playerTwoScored()

        assertThat(game.printScore()).isEqualTo("deuce")

    }

    @Test
    fun `should display 'deuce' when player two has advantage and player one scored`() {
        playerTwoHasAdvantage()
        game.playerOneScored()

        assertThat(game.printScore()).isEqualTo("deuce")

    }

    @Test
    fun `should display 'player one wins' when he scores four times`() {
        playerOneScoreThreeTimes()
        game.playerOneScored()
        assertThat(game.printScore()).isEqualTo("player one wins")
    }

    @Test
    fun `should display 'player two wins' when he scores four times`() {
        playerTwoScoreThreeTimes()
        game.playerTwoScored()
        assertThat(game.printScore()).isEqualTo("player two wins")
    }

    private fun playerTwoHasAdvantage() {
        deuce()
        game.playerTwoScored()
    }

    private fun playerOneHasAdvantage() {
        deuce()
        game.playerOneScored()
    }

    private fun playerOneScoreThreeTimes() {
        game.playerOneScored()
        game.playerOneScored()
        game.playerOneScored()
    }

    private fun playerTwoScoreThreeTimes() {
        game.playerTwoScored()
        game.playerTwoScored()
        game.playerTwoScored()
    }

    private fun deuce() {
        playerOneScoreThreeTimes()
        playerTwoScoreThreeTimes()
    }
}
