package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopItem

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items")
    fun getAllShopItems(): Flow<List<ShopItem>>

    @Delete
    suspend fun deleteShopItem(shopItem: ShopItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(shopItem: ShopItem)
}