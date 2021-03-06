package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopItem

/**
 * DAO interface created to manage ShopItems.
 */
@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items WHERE shopListId=:shopListId ORDER BY is_checked")
    fun findShopItemsForList(shopListId: Int): Flow<List<ShopItem>>

    @Delete
    suspend fun deleteShopItem(shopItem: ShopItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(shopItem: ShopItem)

    @Update
    suspend fun updateShopItemChecked(shopItem: ShopItem)
}