package com.gildedrose

class ItemAgedBrie(item: Item, private val qualityUp: Int, val sellN: Int) :ItemDecorator(item) {

    override fun sell(): ItemAgedBrie {
        item.sellIn-=sellN
        return this
    }


    override fun updateQuality(updateQualityLambda: (Int, Item) -> Int) {

        this.item.quality = Math.min(50, updateQualityLambda(this.qualityUp, this.item))
    }

}