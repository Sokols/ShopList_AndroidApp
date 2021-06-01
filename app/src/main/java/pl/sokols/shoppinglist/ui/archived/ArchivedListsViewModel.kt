package pl.sokols.shoppinglist.ui.archived

import androidx.lifecycle.ViewModel
import pl.sokols.shoppinglist.data.repository.ShopItemRepository
import pl.sokols.shoppinglist.data.repository.ShopListRepository
import javax.inject.Inject

class ArchivedListsViewModel @Inject constructor(
    private val shopItemRepository: ShopItemRepository,
    private val shopListRepository: ShopListRepository
): ViewModel() {
    // TODO: Implement the ViewModel
}