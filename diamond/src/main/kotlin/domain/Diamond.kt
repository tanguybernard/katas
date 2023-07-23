package domain

class Diamond {
    companion object {
        fun create(letter: Char): String {

            val diff = letter.lowercaseChar() - 'A'.lowercaseChar()

            if(!letter.equals('A')) {
                return loopAndReversed(letter, diff)
            }
            return "A"

        }


        fun loop(letter: Char, diff: Int): String {

            var d =  diff
            var diamond = ""
            var middle = 0

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

        /**
         * Iterate alphabet and apply a symmetry (reversed string)
         */
        fun loopAndReversed(letter: Char, diff: Int): String {

            var gapStartEnd =  diff
            var diamond = ""
            var gapMiddle =0

            diamond+= createA(gapStartEnd)

            gapMiddle=1
            for (ch in 'B' until  letter) {
                gapStartEnd-=1
                diamond+="\n"
                diamond+=" ".repeat( gapStartEnd ) +ch+" ".repeat( gapMiddle )+ch +" ".repeat( gapStartEnd )
                gapMiddle+=2
            }

            val reversed = diamond.reversed()
            gapStartEnd-=1
            diamond+="\n"
            diamond+=" ".repeat( gapStartEnd ) +letter+" ".repeat( gapMiddle )+letter +" ".repeat( gapStartEnd )


            return diamond +"\n"+reversed

        }

        val createA= { gapStartEnd: Int -> " ".repeat( gapStartEnd ) + 'A' +" ".repeat( gapStartEnd )}

    }

}
