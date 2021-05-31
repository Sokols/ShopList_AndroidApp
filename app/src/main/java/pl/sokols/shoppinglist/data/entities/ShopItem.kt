package pl.sokols.shoppinglist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val amount: Double,
    @ColumnInfo(name = "is_checked")
    val isChecked: Boolean
)