package pl.sokols.shoppinglist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shop_lists")
data class ShopList(
    val name: String,
    val date: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}