package pl.sokols.shoppinglist.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.databinding.ListDetailsFragmentBinding
import pl.sokols.shoppinglist.ui.adapters.DetailsAdapter
import pl.sokols.shoppinglist.ui.adapters.DividerItemDecorator
import pl.sokols.shoppinglist.utils.*

@AndroidEntryPoint
class ListDetailsFragment : Fragment() {

    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var binding: ListDetailsFragmentBinding
    private val viewModel: ListDetailsViewModel by viewModels()
    private var shopListId: Int? = null
    private var shopListIsActive: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        shopListId = arguments?.getInt(Utils.SHOP_LIST_ID_KEY)
        shopListIsActive = arguments?.getBoolean(Utils.SHOP_LIST_IS_ACTIVE_KEY)
        binding = ListDetailsFragmentBinding.inflate(inflater, container, false)
        detailsAdapter = DetailsAdapter(onCheckClickListener, longClickListener, shopListIsActive!!)
        setComponents()
        setObservers()
        return binding.root
    }

    private fun setComponents() {
        binding.listDetailsRecyclerView.adapter = detailsAdapter
        binding.listDetailsRecyclerView.addItemDecoration(
            DividerItemDecorator(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.divider,
                    null
                )!!
            )
        )

        if (shopListIsActive == true) {
            addSwipeToDelete()
            binding.addItemFAB.setOnClickListener {
                addNewShopItem(null, object : OnItemClickListener {
                    override fun onItemClickListener(item: Any) {
                        viewModel.addShopItem(item as ShopItem)
                    }
                })
            }
        } else {
            binding.addItemFAB.visibility = View.GONE
        }
    }

    private fun setObservers() {
        viewModel.items.observe(viewLifecycleOwner, { shopItems ->
            detailsAdapter.submitList(shopItems) {
                binding.listDetailsRecyclerView.scrollToPosition(0)
            }
        })
    }

    private fun addNewShopItem(shopItem: ShopItem?, listener: OnItemClickListener) {
        ShopItemDialog(shopItem, shopListId!!, listener).show(
            requireFragmentManager(),
            getString(R.string.provide_item_dialog)
        )
    }

    private val onCheckClickListener = object : OnItemClickListener {
        override fun onItemClickListener(item: Any) {
            val shopItem = item as ShopItem
            shopItem.isChecked = !shopItem.isChecked
            viewModel.updateShopItem(shopItem)
            detailsAdapter.notifyDataSetChanged()

            Utils.getSnackbar(
                requireView(),
                if (shopItem.isChecked) {
                    getString(R.string.checked)
                } else {
                    getString(R.string.unchecked)
                },
                requireActivity()
            ).setAction(getString(R.string.undo)) {
                shopItem.isChecked = !shopItem.isChecked
                viewModel.updateShopItem(shopItem)
                detailsAdapter.notifyDataSetChanged()
            }.show()
        }
    }

    private fun addSwipeToDelete() {
        ItemTouchHelper(object : SwipeHelper(ItemTouchHelper.LEFT) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem: ShopItem =
                    detailsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(deletedItem)

                Utils.getSnackbar(
                    requireView(),
                    String.format(getString(R.string.deleted), deletedItem.name),
                    requireActivity()
                ).show()
            }
        }).attachToRecyclerView(binding.listDetailsRecyclerView)
    }

    private val longClickListener = object : OnLongClickListener {
        override fun onLongClickListener(item: Any) {
            addNewShopItem(item as ShopItem, object : OnItemClickListener {
                override fun onItemClickListener(item: Any) {
                    viewModel.updateShopItem(item as ShopItem)
                    detailsAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}