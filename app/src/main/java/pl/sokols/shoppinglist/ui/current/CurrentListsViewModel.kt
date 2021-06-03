package pl.sokols.shoppinglist.ui.current

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
class CurrentListsViewModel @Inject constructor(
    private val shopListRepository: ShopListRepository,
    private val shopItemRepository: ShopItemRepository
) : ViewModel() {

    val items: LiveData<List<ShopList>> = shopListRepository.getAllShopListsByActive(true).asLiveData()

    fun addShopList(shopList: ShopList) = viewModelScope.launch {
        shopListRepository.insertShopList(shopList)
    }

    fun deleteShopList(deletedList: ShopList) = viewModelScope.launch {
        shopListRepository.deleteShopList(deletedList)
    }

    fun updateShopList(shopList: ShopList) = viewModelScope.launch {
        shopListRepository.updateShopListActive(shopList.id, shopList.isActive)
    }
}