package pl.sokols.shoppinglist.ui.current

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.repository.ShopItemRepository
import pl.sokols.shoppinglist.data.repository.ShopListRepository
import javax.inject.Inject

@HiltViewModel
class CurrentListsViewModel @Inject constructor(
    private val shopItemRepository: ShopItemRepository,
    private val shopListRepository: ShopListRepository
) : ViewModel() {

    val items: LiveData<List<ShopList>> = shopListRepository.allShopLists.asLiveData()

    fun addShopList(shopList: ShopList) = viewModelScope.launch {
        shopListRepository.insertShopList(shopList)
    }
}