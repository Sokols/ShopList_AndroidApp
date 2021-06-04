package pl.sokols.shoppinglist.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import pl.sokols.shoppinglist.data.entities.ShopListDetails

/**
 * Callback created to compare ShopLists in ListAdapter objects.
 */
object ShopListDiffCallback : DiffUtil.ItemCallback<ShopListDetails>() {
    override fun areItemsTheSame(oldItem: ShopListDetails, newItem: ShopListDetails): Boolean {
        return oldItem.shopList.id == newItem.shopList.id
    }

    override fun areContentsTheSame(oldItem: ShopListDetails, newItem: ShopListDetails): Boolean {
        return oldItem == newItem
    }
}
