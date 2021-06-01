package pl.sokols.shoppinglist.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.entities.ShopList

@Dao
interface ShopListDao {

    @Transaction
    @Query("SELECT * FROM shop_lists")
    fun getAllShopLists(): Flow<List<ShopList>>

    @Delete
    suspend fun deleteShopList(shopList: ShopList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopList(shopList: ShopList)
}