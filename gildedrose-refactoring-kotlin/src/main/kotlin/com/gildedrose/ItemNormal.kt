package com.gildedrose

class ItemNormal(item: Item, private val qualityUp: Int, val sellN: Int) :ItemDecorator(item) {

    override fun sell(): ItemNormal {
        item.sellIn-=sellN
        return this
    }


    override fun updateQuality(updateQualityLambda: (Int,Item) -> Int) {

        //Quality cant be negative, min 0
        this.item.quality = Math.max(0, updateQualityLambda(this.qualityUp, this.item))
    }


}