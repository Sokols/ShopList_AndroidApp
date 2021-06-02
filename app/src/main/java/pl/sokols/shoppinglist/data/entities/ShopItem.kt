package pl.sokols.shoppinglist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "shop_items",
    foreignKeys = [ForeignKey(
        entity = ShopList::class,
        parentColumns = ["id"],
        childColumns = ["shop_list_id"],
        onDelete = CASCADE
    )]
)
data class ShopItem(
    var name: String?,
    var amount: Int?,
    @ColumnInfo(name = "shop_list_id")
    val shopListId: Int,
    @ColumnInfo(name = "is_checked")
    val isChecked: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shop_item_id")
    var shopItemId: Int = 0
}