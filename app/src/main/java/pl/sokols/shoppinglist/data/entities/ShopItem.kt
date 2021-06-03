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
        childColumns = ["shopListId"],
        onDelete = CASCADE
    )]
)
data class ShopItem(
    var name: String,
    var amount: Int,
    var shopListId: Int,
    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}