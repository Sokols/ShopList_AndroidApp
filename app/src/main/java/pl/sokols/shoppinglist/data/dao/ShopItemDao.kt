package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopItem

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items WHERE shopListId=:shopListId ORDER BY is_checked")
    fun findShopItemsForList(shopListId: Int): Flow<List<ShopItem>>

    @Delete
    suspend fun deleteShopItem(shopItem: ShopItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(shopItem: ShopItem)

    @Query("UPDATE shop_items SET is_checked=:isChecked WHERE id=:id")
    suspend fun updateShopItemChecked(id: Int, isChecked: Boolean)
}