package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GameTest {

    var g = Game();

    @Test
    fun aFrameWith0ShouldReturnAScoreEqualTo0(){

        var game = Game()

        game.roll(0);
        game.roll(0);

        assertThat(game.score()).isEqualTo(0)

    }


    @Test
    fun first1SEcond2Return3(){

        val game = Game()

        game.roll(1);
        game.roll(2);

        assertThat(game.score()).isEqualTo(3)

        assertEquals(0, game.score());
    }

    @Test
    fun firstFrameSpareSecond1Score12(){

        val game = Game()

        game.roll(5)
        game.roll(5)
        game.roll(1)
        game.roll(0)

        assertThat(game.score()).isEqualTo(12)

    }

    @Test
    fun firstFrameSpareSecond0Score10(){

        val game = Game()

        game.roll(5)
        game.roll(5)
        game.roll(0)
        game.roll(0)

        assertThat(game.score()).isEqualTo(10)

    }
    @Test
    fun SpareSpare1And2(){

        val game = Game()

        game.roll(5)
        game.roll(5)
        game.roll(5)
        game.roll(5)
        game.roll(1)
        game.roll(2)

        assertThat(game.score()).isEqualTo(36)

    }

}