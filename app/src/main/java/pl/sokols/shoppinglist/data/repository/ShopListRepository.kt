package pl.sokols.shoppinglist.data.repository

import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.dao.ShopListDao
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.entities.ShopListDetails
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module with prepared ShopList's methods.
 */
@Singleton
class ShopListRepository @Inject constructor(
    private val shopListDao: ShopListDao
) {
    fun getAllShopListsByActive(isActive: Boolean): Flow<List<ShopListDetails>> =
        shopListDao.getAllShopListsByActive(isActive)

    suspend fun deleteShopList(shopList: ShopList) = shopListDao.deleteShopList(shopList)

    suspend fun insertShopList(shopList: ShopList) = shopListDao.insertShopList(shopList)

    suspend fun updateShopList(shopList: ShopList) = shopListDao.updateShopList(shopList)
}