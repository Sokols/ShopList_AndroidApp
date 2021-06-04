package pl.sokols.shoppinglist.ui.archived

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

/**
 * ViewModel for the ArchivedLists fragment.
 */
@HiltViewModel
class ArchivedListsViewModel @Inject constructor(
    private val shopListRepository: ShopListRepository
) : ViewModel() {

    val items: LiveData<List<ShopListDetails>> =
        shopListRepository.getAllShopListsByActive(false).asLiveData()

    fun deleteShopList(deletedList: ShopList) = viewModelScope.launch {
        shopListRepository.deleteShopList(deletedList)
    }

    fun updateShopList(shopList: ShopList) = viewModelScope.launch {
        shopListRepository.updateShopList(shopList)
    }
}