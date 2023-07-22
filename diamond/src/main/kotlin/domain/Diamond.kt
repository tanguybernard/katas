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

                diamond+="\n"

                diamond+='C'+" ".repeat( diff+1 )+'C'

                diamond+="\n"
                diamond+=" ".repeat( d ) +'B'+" ".repeat( d )+'B' +" ".repeat( d )

                d+=1
                diamond+="\n"
                diamond+= " ".repeat( d ) + 'A' +" ".repeat( d )

                return  diamond

            }


                return "A"
        }
    }

}
