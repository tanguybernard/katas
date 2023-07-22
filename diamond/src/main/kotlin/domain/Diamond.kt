package domain

class Diamond {
    companion object {
        fun create(letter: Char): String {

            val diff = letter.lowercaseChar() - 'A'.lowercaseChar()

            if(!letter.equals('A')) {
                return loop(letter, diff)
            }
            return "A"

        }


        fun loop(letter: Char, diff: Int): String {

            var d =  diff;
            var diamond = ""

            var middle = 0;

            diamond+= " ".repeat( d ) + 'A' +" ".repeat( d )

            middle=1
            for (ch in 'B' .. letter) {
                d-=1
                diamond+="\n"
                diamond+=" ".repeat( d ) +ch+" ".repeat( middle )+ch +" ".repeat( d )
                middle+=2
            }

            middle-=2

            for (ch in letter-1 downTo 'B') {
                d+=1
                middle-=2
                diamond+="\n"
                diamond+=" ".repeat( d ) +ch+" ".repeat( middle )+ch +" ".repeat( d )

            }

            d+=1
            diamond+="\n"
            diamond+= " ".repeat( d ) + 'A' +" ".repeat( d )

            return diamond

        }
    }

}
