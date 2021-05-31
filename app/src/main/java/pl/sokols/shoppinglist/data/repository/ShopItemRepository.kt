package pl.sokols.shoppinglist.data.repository

import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.dao.ShopItemDao
import pl.sokols.shoppinglist.data.entities.ShopItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopItemRepository @Inject constructor(
    private val shopItemDao: ShopItemDao
) {

    val allShopItems: Flow<List<ShopItem>> = shopItemDao.getAllShopItems()

    suspend fun deleteShopItem(shopItem: ShopItem) = shopItemDao.deleteShopItem(shopItem)

    suspend fun insertShopItem(shopItem: ShopItem) = shopItemDao.insertShopItem(shopItem)
}