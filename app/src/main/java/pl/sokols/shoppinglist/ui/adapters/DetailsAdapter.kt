package pl.sokols.shoppinglist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.databinding.ShopItemListitemBinding
import pl.sokols.shoppinglist.ui.adapters.DetailsAdapter.DetailsViewHolder
import pl.sokols.shoppinglist.utils.OnItemClickListener
import pl.sokols.shoppinglist.utils.OnLongClickListener

class DetailsAdapter(
    private val listener: OnItemClickListener,
    private val longClickListener: OnLongClickListener,
    private val isActive: Boolean
) : ListAdapter<ShopItem, DetailsViewHolder>(ShopItemDiffCallback) {

    inner class DetailsViewHolder(
        private val binding: ShopItemListitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            shopItem: ShopItem,
            listener: OnItemClickListener,
            longClickListener: OnLongClickListener,
            isActive: Boolean
        ) {
            if (shopItem.isChecked) {
                binding.shopItemCheckImageView.setImageResource(R.drawable.ic_baseline_check_circle_24)
            } else {
                binding.shopItemCheckImageView.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
            }

            binding.shopItem = shopItem

            if (isActive) {
                binding.shopItemLayout.setOnClickListener {
                    listener.onItemClickListener(shopItem)
                }

                binding.shopItemLayout.setOnLongClickListener {
                    longClickListener.onLongClickListener(shopItem)
                    true
                }
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
        holder.bind(getItem(position), listener, longClickListener, isActive)
    }
}