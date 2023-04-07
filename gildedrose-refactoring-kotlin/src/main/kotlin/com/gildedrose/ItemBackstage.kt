package com.gildedrose

class ItemBackstage(item: Item, private val qualityUp: Int, val sellN: Int) :ItemDecorator(item) {

    override fun sell(): ItemBackstage {
        item.sellIn-=sellN
        return this
    }


    override fun updateQuality(updateQualityLambda: (Int,Item) -> Int) {

        //Quality cant be negative, min 0


        val res = when {
            item.sellIn <= 0 -> 0
            item.sellIn < 6 -> item.quality+(qualityUp*3)
            item.sellIn < 11 -> item.quality+ (qualityUp*2)
            else -> {
                item.quality+qualityUp
            }
        }
        println("sell: "+item.sellIn)

        println(res)

        this.item.quality = Math.min(50, res);

    }


}