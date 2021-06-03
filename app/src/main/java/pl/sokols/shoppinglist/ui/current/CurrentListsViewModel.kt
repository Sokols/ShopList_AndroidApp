package pl.sokols.shoppinglist.ui.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.entities.ShopListDetails
import pl.sokols.shoppinglist.data.repository.ShopListRepository
import javax.inject.Inject

@HiltViewModel
class CurrentListsViewModel @Inject constructor(
    private val shopListRepository: ShopListRepository
) : ViewModel() {

    val items: LiveData<List<ShopListDetails>> = shopListRepository.getAllShopListsByActive(true).asLiveData()

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