package pl.sokols.shoppinglist.utils

import androidx.recyclerview.widget.DiffUtil
import pl.sokols.shoppinglist.data.entities.ShopList

object ShopListDiffCallback : DiffUtil.ItemCallback<ShopList>() {
    override fun areItemsTheSame(oldItem: ShopList, newItem: ShopList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopList, newItem: ShopList): Boolean {
        return oldItem == newItem
    }
}
