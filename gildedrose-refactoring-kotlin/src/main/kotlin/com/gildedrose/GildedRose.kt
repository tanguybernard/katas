package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            if (items[i].name != "Aged Brie" && items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                if (items[i].quality > 0) {
                    if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                        items[i].quality = items[i].quality - 1
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1

                    if (items[i].name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }
                    }
                }
            }

            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                items[i].sellIn = items[i].sellIn - 1
            }

            if (items[i].sellIn < 0) {
                if (items[i].name != "Aged Brie") {
                    if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (items[i].quality > 0) {
                            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                                items[i].quality = items[i].quality - 1
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1
                    }
                }
            }
        }
    }



    //----------- V2 with strategic pattern ----------------//

    fun updateQualityV2() {
        items.map {  strategy(it).execute(it) }
    }



    interface ItemStrategy{
        fun execute(item: Item): Item
    }

    class AgedBrieStrategy : ItemStrategy {
        override fun execute(item : Item): Item{


            var q = 0;
            if(item.quality <50) {
                q = 1
                if(item.sellIn<=0){
                    q = 2
                }

            }
            item.quality = Math.min(50,item.quality+q)
            item.sellIn -= 1
            return item

        }

    }

    class BackstageStrategy : ItemStrategy {
        override fun execute(item : Item): Item {

            var q = 0

            when (item.sellIn) {
                in Int.MIN_VALUE..5 -> q+=3
                in 6..  10 -> q+=2
                else -> {
                    q+=1
                }
            }

            if(item.quality +q >=50) {
                item.quality = 50
            }
            else {
                item.quality +=q
            }

            if(item.sellIn <1){
                item.quality = 0
            }

            item.sellIn -= 1
            return item
        }

    }

    class ClassicStrategy : ItemStrategy {
        override fun execute(item : Item): Item {
            if(item.sellIn > 1) {
                item.quality -=1
            }
            else {
                item.quality -=2
            }

            if(item.quality < 0) {
                item.quality = 0
            }

            item.sellIn -=1


            return item
        }

    }
    class SulfurasStrategy:ItemStrategy {
        override fun execute(item: Item): Item {
            return item
        }

    }

    class ConjuredStrategy:ItemStrategy {
        override fun execute(item: Item): Item {

            item.sellIn -=1

            item.quality -=2

            if(item.quality < 0) {
                item.quality = 0
            }
            return item
        }

    }


    fun strategy(item: Item): ItemStrategy {

        when (item.name) {
            "Aged Brie" -> return AgedBrieStrategy()
            "Backstage passes to a TAFKAL80ETC concert" -> return BackstageStrategy()
            "Sulfuras, Hand of Ragnaros" -> return SulfurasStrategy()
            "Conjured Mana Cake" -> return ConjuredStrategy()
            else -> {
                return ClassicStrategy()
            }
        }

    }

    //----------- V2 functional ----------------//


    val lambdaQuality:(Int,Item)->Int={value: Int , item: Item -> item.quality+ if (item.sellIn - 1 < 0) (value * 2) else value}


    val createItem:(Item) -> ItemDecorator = { item: Item ->
        when (item.name) {
            "Aged Brie" -> ItemAgedBrie(item,1, 1)
            "Sulfuras, Hand of Ragnaros" -> ItemSulfuras(item,0, 0)
            "Backstage passes to a TAFKAL80ETC concert" -> ItemBackstage(item,1,1)
            "Conjured Mana Cake" -> ItemNormal(item,-2, 1)// twice as fast as normal items
            else -> {
                ItemNormal(item,-1, 1)
            }
        }
    }


    fun updateQualityFunctional() {


        items.map {
           it
               .let { item -> createItem(item) }
               .let { item -> item.updateQuality(lambdaQuality);item }
               .let { item -> item.sell()}
       }
    }



}


