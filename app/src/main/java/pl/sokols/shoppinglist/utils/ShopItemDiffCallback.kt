package pl.sokols.shoppinglist.utils

import androidx.recyclerview.widget.DiffUtil
import pl.sokols.shoppinglist.data.entities.ShopItem

object ShopItemDiffCallback : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.shopItemId == newItem.shopItemId
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}