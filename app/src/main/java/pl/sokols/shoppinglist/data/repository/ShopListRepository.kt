package pl.sokols.shoppinglist.data.repository

import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.dao.ShopListDao
import pl.sokols.shoppinglist.data.entities.ShopList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopListRepository @Inject constructor(
    private val shopListDao: ShopListDao
) {
    val allShopLists: Flow<List<ShopList>> = shopListDao.getAllShopLists()

    suspend fun deleteShopList(shopList: ShopList) = shopListDao.deleteShopList(shopList)

    suspend fun insertShopList(shopList: ShopList) = shopListDao.insertShopList(shopList)
}