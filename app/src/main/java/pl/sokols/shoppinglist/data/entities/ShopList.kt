package pl.sokols.shoppinglist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_lists")
data class ShopList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @Embedded(prefix = "list_")
    val items: List<ShopItem>,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean
)