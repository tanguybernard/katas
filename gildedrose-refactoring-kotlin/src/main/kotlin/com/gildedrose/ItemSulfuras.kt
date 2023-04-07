package com.gildedrose

class ItemSulfuras(item: Item, private val qualityUp: Int, val sellN: Int) :ItemDecorator(item) {

    override fun sell(): ItemSulfuras {
        item.sellIn-=sellN
        return this
    }


    override fun updateQuality(updateQualityLambda: (Int,Item) -> Int) {

        updateQualityLambda(qualityUp, this.item)

    }

}