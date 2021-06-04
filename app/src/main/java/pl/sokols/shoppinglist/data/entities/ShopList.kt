package pl.sokols.shoppinglist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shop_lists",)
data class ShopList(
    var name: String,
    var date: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "is_active")
    var isActive: Boolean = true,
    @Ignore
    var items: List<ShopItem> = listOf()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(name: String) : this(
        name = name,
        date = Calendar.getInstance().time,
        isActive = true
    )

    constructor(shopListDetails: ShopListDetails) : this(
        name = shopListDetails.shopList.name,
        date = shopListDetails.shopList.date,
        isActive = shopListDetails.shopList.isActive,
        items = shopListDetails.items
    )
}