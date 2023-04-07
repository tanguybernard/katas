package com.gildedrose

abstract class ItemDecorator(protected var item: Item) :Item(item.name, item.sellIn, item.quality) {

    abstract fun updateQuality(updateQualityLambda: (Int, Item) -> Int)

    abstract fun sell(): ItemDecorator

}