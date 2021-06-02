package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopList

@Dao
interface ShopListDao {

    @Transaction
    @Query("SELECT * FROM shop_lists WHERE is_active=:isActive ORDER BY date DESC")
    fun getAllShopListsByActive(isActive: Boolean): Flow<List<ShopList>>

    @Delete
    suspend fun deleteShopList(shopList: ShopList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopList(shopList: ShopList)

    @Query("UPDATE shop_lists SET is_active=:isActive WHERE id=:id")
    suspend fun updateShopListActive(id: Int, isActive: Boolean)
}