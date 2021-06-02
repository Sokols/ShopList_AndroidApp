package pl.sokols.shoppinglist.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.databinding.ShopItemListitemBinding
import pl.sokols.shoppinglist.ui.details.adapters.DetailsAdapter.DetailsViewHolder
import pl.sokols.shoppinglist.utils.OnItemClickListener
import pl.sokols.shoppinglist.utils.ShopItemDiffCallback

class DetailsAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<ShopItem, DetailsViewHolder>(ShopItemDiffCallback) {

    inner class DetailsViewHolder(
        private val binding: ShopItemListitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shopItem: ShopItem, onItemClickListener: OnItemClickListener) {
            binding.shopItem = shopItem

            binding.shopItemCheckImageView.setOnClickListener {
                onItemClickListener.onItemClickListener(shopItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder(
            ShopItemListitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}