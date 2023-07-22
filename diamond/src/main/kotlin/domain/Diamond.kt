package domain

class Diamond {
    companion object {
        fun create(letter: Char): String {

            val diff = letter.lowercaseChar() - 'A'.lowercaseChar()




            //TODO loop alphabet and reduce diff each round
            var diamond = ""
            if(letter.equals('B')) {

                diamond+= " ".repeat( diff ) + 'A' +" ".repeat( diff )
                diamond+="\n"
                diamond+='B'+" ".repeat( diff )+'B'
                diamond+="\n"
                diamond+= " ".repeat( diff ) + 'A' +" ".repeat( diff )

                return  diamond

            }

            var d = diff
            if(letter.equals('C')) {

                diamond+= " ".repeat( d ) + 'A' +" ".repeat( d )

                d-=1
                diamond+="\n"

                diamond+=" ".repeat( d ) +'B'+" ".repeat( d )+'B' +" ".repeat( d )
                d-=1
                diamond+="\n"
                diamond+=" ".repeat( d ) +'C'+" ".repeat( diff+1 )+'C' +" ".repeat( d )

                d+=1
                diamond+="\n"
                diamond+=" ".repeat( d ) +'B'+" ".repeat( d )+'B' +" ".repeat( d )

                d+=1
                diamond+="\n"
                diamond+= " ".repeat( d ) + 'A' +" ".repeat( d )

                return  diamond

            }

            if(letter.equals('D') or letter.equals('E')) {

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
            for (ch in 'B'.until(letter)) {
                d-=1
                diamond+="\n"
                diamond+=" ".repeat( d ) +ch+" ".repeat( middle )+ch +" ".repeat( d )
                middle+=2
            }

            d-=1
            diamond+="\n"
            diamond+=" ".repeat( d ) +letter+" ".repeat( middle )+letter +" ".repeat( d )




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
