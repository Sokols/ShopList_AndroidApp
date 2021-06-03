package pl.sokols.shoppinglist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopListDetails
import pl.sokols.shoppinglist.databinding.ShopListListitemBinding
import pl.sokols.shoppinglist.utils.Utils

class ListsAdapter :
    ListAdapter<ShopListDetails, ListsAdapter.ListsViewHolder>(ShopListDiffCallback) {

    inner class ListsViewHolder(
        private val binding: ShopListListitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shopList: ShopListDetails) {
            binding.shopList = shopList

            binding.doneAmount = "${Utils.getChecked(shopList.items)}/${shopList.items.size}"

            binding.shopListLayout.setOnClickListener {
                val bundle = bundleOf(
                    Utils.SHOP_LIST_ID_KEY to shopList.shopList.id,
                    Utils.SHOP_LIST_IS_ACTIVE_KEY to shopList.shopList.isActive
                )
                if (shopList.shopList.isActive) {
                    it.findNavController()
                        .navigate(R.id.action_currentListsFragment_to_listDetailsFragment, bundle)
                } else {
                    it.findNavController()
                        .navigate(R.id.action_archivedListsFragment_to_listDetailsFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsViewHolder {
        return ListsViewHolder(
            ShopListListitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}