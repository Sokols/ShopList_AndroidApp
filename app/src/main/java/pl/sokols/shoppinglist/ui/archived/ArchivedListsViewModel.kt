package pl.sokols.shoppinglist.ui.archived

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.repository.ShopItemRepository
import pl.sokols.shoppinglist.data.repository.ShopListRepository
import javax.inject.Inject

@HiltViewModel
class ArchivedListsViewModel @Inject constructor(
    private val shopListRepository: ShopListRepository,
    private val shopItemRepository: ShopItemRepository
): ViewModel() {

    val items: LiveData<List<ShopList>> = shopListRepository.getAllShopListsByActive(false).asLiveData()

    fun deleteShopList(deletedList: ShopList) = viewModelScope.launch {
        shopListRepository.deleteShopList(deletedList)
    }

    fun updateShopList(shopList: ShopList) = viewModelScope.launch {
        shopListRepository.updateShopListActive(shopList.id, shopList.isActive)
    }

    fun addShopList(shopList: ShopList) = viewModelScope.launch {
        shopListRepository.insertShopList(shopList)
    }
}