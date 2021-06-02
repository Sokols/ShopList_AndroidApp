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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.databinding.ListDetailsFragmentBinding
import pl.sokols.shoppinglist.ui.details.adapters.DetailsAdapter
import pl.sokols.shoppinglist.utils.DividerItemDecorator
import pl.sokols.shoppinglist.utils.OnItemClickListener
import pl.sokols.shoppinglist.utils.ShopItemDialog
import pl.sokols.shoppinglist.utils.Utils

@AndroidEntryPoint
class ListDetailsFragment : Fragment() {

    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var binding: ListDetailsFragmentBinding
    private val viewModel: ListDetailsViewModel by viewModels()
    private var shopListId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        shopListId = arguments?.getInt(Utils.SHOP_LIST_ID_KEY)
        binding = ListDetailsFragmentBinding.inflate(inflater, container, false)
        detailsAdapter = DetailsAdapter(onItemClickListener)
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

        addSwipeToDelete()

        binding.addItemFAB.setOnClickListener {
            addNewShopItem()
        }
    }

    private fun addNewShopItem() {
        ShopItemDialog(shopListId!!, object : OnItemClickListener {
            override fun onItemClickListener(item: Any) {
                viewModel.addShopItem(item as ShopItem)
            }
        }).show(requireFragmentManager(), getString(R.string.provide_item_dialog))
    }

    private fun setObservers() {
        viewModel.items.observe(viewLifecycleOwner, { shopItems ->
            val clones = mutableListOf<ShopItem>()
            shopItems.forEach { item -> clones.add(item.clone()) }
            detailsAdapter.submitList(clones) {
                binding.listDetailsRecyclerView.scrollToPosition(0)
            }
        })
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClickListener(item: Any) {
            val shopItem = item as ShopItem
            shopItem.isChecked = !shopItem.isChecked
            viewModel.updateShopItem(shopItem)
            Snackbar.make(
                requireView(),
                if (shopItem.isChecked) {
                    getString(R.string.checked)
                } else {
                    getString(R.string.unchecked)
                },
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun addSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem: ShopItem =
                    detailsAdapter.currentList[viewHolder.adapterPosition]

                viewModel.deleteShopItem(deletedItem)

                Snackbar.make(
                    requireView(),
                    String.format(getString(R.string.deleted), deletedItem.name),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.undo)) {
                        viewModel.addShopItem(deletedItem)
                    }.show()
            }
        }).attachToRecyclerView(binding.listDetailsRecyclerView)
    }
}