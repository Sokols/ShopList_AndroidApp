package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopItem

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items WHERE shop_list_id=:shopListId")
    fun findShopItemsForList(shopListId: Int): Flow<List<ShopItem>>

    @Delete
    suspend fun deleteShopItem(shopItem: ShopItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(shopItem: ShopItem)
}