package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiamondTest {


    @Test
    fun letterA() {
        val result = Diamond.create('A')

        assertThat(result).isEqualTo("A")
    }

    @Test
    fun letterB() {
        val result = Diamond.create('B')

        assertThat(result).isEqualTo(" A \nB B\n A ")

    }

    @Test
    fun letterC() {
        assertThat(Diamond.create('C')).isEqualTo(
              "  A  \n"
            + " B B \n"
            + "C   C\n"
            + " B B \n"
            + "  A  ",
        )
    }


}