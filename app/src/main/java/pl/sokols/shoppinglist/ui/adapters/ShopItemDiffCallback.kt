package pl.sokols.shoppinglist.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import pl.sokols.shoppinglist.data.entities.ShopItem

/**
 * Callback created to compare ShopItems in ListAdapter objects.
 */
object ShopItemDiffCallback : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}