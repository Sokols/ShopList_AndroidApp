package pl.sokols.shoppinglist.ui.details

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.data.repository.ShopItemRepository
import pl.sokols.shoppinglist.utils.Utils
import javax.inject.Inject

/**
 * ViewModel for the ShopItems details fragment.
 */
@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val shopItemRepository: ShopItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private var shopListId: Int = savedStateHandle.get<Int>(Utils.SHOP_LIST_ID_KEY)!!

    val items: LiveData<List<ShopItem>> =
        shopItemRepository.getAllShopItems(shopListId).asLiveData()

    fun addShopItem(shopItem: ShopItem) = viewModelScope.launch {
        shopItemRepository.insertShopItem(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) = viewModelScope.launch {
        shopItemRepository.deleteShopItem(shopItem)
    }

    fun updateShopItem(shopItem: ShopItem) = viewModelScope.launch {
        shopItemRepository.updateShopItemChecked(shopItem)
    }
}