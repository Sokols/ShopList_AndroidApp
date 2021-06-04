package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.entities.ShopListDetails

/**
 * DAO interface created to manage ShopLists.
 */
@Dao
interface ShopListDao {

    @Transaction
    @Query("SELECT * FROM shop_lists WHERE is_active=:isActive ORDER BY date DESC")
    fun getAllShopListsByActive(isActive: Boolean): Flow<List<ShopListDetails>>

    @Delete
    suspend fun deleteShopList(shopList: ShopList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopList(shopList: ShopList)

    @Update
    suspend fun updateShopList(shopList: ShopList)
}