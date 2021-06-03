package pl.sokols.shoppinglist.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ShopListDetails(
    @Embedded
    val shopList: ShopList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shopListId",
        entity = ShopItem::class
    )
    val items: List<ShopItem> = listOf()
)