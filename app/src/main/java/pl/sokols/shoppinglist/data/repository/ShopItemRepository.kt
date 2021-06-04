package pl.sokols.shoppinglist.data.repository

import kotlinx.coroutines.flow.Flow
import pl.sokols.shoppinglist.data.dao.ShopItemDao
import pl.sokols.shoppinglist.data.entities.ShopItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module with prepared ShopItem's methods.
 */
@Singleton
class ShopItemRepository @Inject constructor(
    private val shopItemDao: ShopItemDao
) {

    fun getAllShopItems(shopListId: Int): Flow<List<ShopItem>> =
        shopItemDao.findShopItemsForList(shopListId)

    suspend fun deleteShopItem(shopItem: ShopItem) = shopItemDao.deleteShopItem(shopItem)

    suspend fun insertShopItem(shopItem: ShopItem) = shopItemDao.insertShopItem(shopItem)

    suspend fun updateShopItemChecked(shopItem: ShopItem) = shopItemDao.updateShopItemChecked(shopItem)
}