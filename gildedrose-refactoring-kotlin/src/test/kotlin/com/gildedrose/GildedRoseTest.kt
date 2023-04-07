package com.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val Backstage = "Backstage passes to a TAFKAL80ETC concert"

internal class GildedRoseTest {

    @Test
    fun foo() {
        val items = listOf(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertEquals("foo", app.items[0].name)

    }

    @Test
    fun `At the end of each day our system lowers both values for every item`() {

        val items = listOf(
            Item("+5 Dexterity Vest", 10, 20),
            Item("Elixir of the Mongoose", 5, 7)
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
            assertThat(listOf(
            Item("+5 Dexterity Vest", 9, 19),
            Item("Elixir of the Mongoose", 4, 6)
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)

    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {

        val items = listOf(
            Item("+5 Dexterity Vest", 0, 4),
            Item("Elixir of the Mongoose", -1, 7)
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("+5 Dexterity Vest", -1, 2),
            Item("Elixir of the Mongoose", -2, 5)
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)

    }

    @Test
    fun `Aged Brie actually increases in Quality the older it gets`(){
        val items = listOf(
            Item("Aged Brie", 1, 4),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("Aged Brie", 0, 5),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }

    @Test
    fun `The Quality of an item is never more than 50`(){
        val items = listOf(
            Item("Aged Brie", 3, 50),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("Aged Brie", 2, 50),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }

    @Test
    fun `The Quality of an item is never more than 50, case 51`(){
        //TODO Maybe the code must prevent the creation of item more than 50 quality ?

        val items = listOf(
            Item("Aged Brie", 3, 51),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("Aged Brie", 2, 50),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }


    @Test
    fun `Sulfuras, being a legendary item, never has to be sold or decreases in Quality`(){

        val items = listOf(
            Item("Sulfuras, Hand of Ragnaros", 3, 40),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("Sulfuras, Hand of Ragnaros", 3, 40),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }

    @Test
    fun `Backstage passes, Quality increases by 2 when there are 10 days or less`(){

        val items = listOf(
            Item(Backstage, 9, 40),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item(Backstage, 8, 42),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }

    @Test
    fun `Backstage passes, Quality increases by 3 when there are 5 days or less`(){

        val items = listOf(
            Item(Backstage, 5, 40),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item(Backstage, 4, 43),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }

    @Test
    fun `Backstage passes, Quality drops to 0 after the concert`(){

        val items = listOf(
            Item(Backstage, 0, 40),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item(Backstage, -1, 0),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)
    }

    @Test
    fun testGivenExample(){

        val items = listOf(
            Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item(Backstage, 15, 20),
            Item(Backstage, 10, 49),
            Item(Backstage, 5, 49),
            Item("Conjured Mana Cake", 3, 6)

        )


        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("+5 Dexterity Vest", 9, 19), //
            Item("Aged Brie", 1, 1), //
            Item("Elixir of the Mongoose", 4, 6), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item(Backstage, 14, 21),
            Item(Backstage, 9, 50),
            Item(Backstage, 4, 50),
            Item("Conjured Mana Cake", 2, 4)

        )).usingRecursiveComparison().isEqualTo(app.items)

    }

    @Test
    fun `Conjured Mana Cake item degrade in Quality twice as fast as normal items`(){

        val items = listOf(
            Item("Conjured Mana Cake", 3, 6),
        )

        val app = GildedRose(items)
        app.updateQualityFunctional()
        assertThat(listOf(
            Item("Conjured Mana Cake", 2, 4),
        )).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(app.items)

    }


    @Test
    fun `16 times`(){

        val items = listOf(
            Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item(Backstage, 15, 17),
            Item(Backstage, 10, 49),
            Item(Backstage, 5, 49),
            Item("Conjured Mana Cake", 3, 6)

        )

        val app = GildedRose(items)
        for(i in 0..15){
            app.updateQualityFunctional()

        }


        assertThat(listOf(
            Item("+5 Dexterity Vest", -6, 0), //
            Item("Aged Brie", -14, 30), //
            Item("Elixir of the Mongoose", -11, 0), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item(Backstage, -1, 0),
            Item(Backstage, -6, 0),
            Item(Backstage, -11, 0),
            Item("Conjured Mana Cake", -13, 0)

        )).usingRecursiveComparison().isEqualTo(app.items)


    }

}


