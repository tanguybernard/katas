package domain

class Game {
    private var score: Int = 0

    var rolls = ArrayList<Int>();



    fun roll(i: Int) {

        rolls.add(i);



    }

   fun score(): Int {


       var launch = 0;

       var scoreFrame = 0;



       var previous = ArrayList<String>();

       rolls.forEach { roll ->
           run {

               launch+=1
               scoreFrame +=roll




               if(launch == 2) {



                   launch = 0

                   if (previous.size >=1 && previous.get(previous.size-1) == "spare"){
                       this.score += scoreFrame;
                   }


                   if(scoreFrame == 10){
                       previous.add("spare")
                   }
                   else{
                       previous.add("nothing")
                   }
                   scoreFrame=0

               }




               this.score += roll;

           }
       }

       return this.score;

   }

}
