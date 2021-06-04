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

/**
 * Fragment of selected ShopItems.
 */
@AndroidEntryPoint
class ListDetailsFragment : Fragment() {

    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var binding: ListDetailsFragmentBinding
    private val viewModel: ListDetailsViewModel by viewModels()

    private var shopListIsActive: Boolean? = null
    private var shopListId: Int? = null             // flag created to specify the calling Fragment

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

    /**
     * Method which:
     * - adds adapter to recyclerview,
     * - adds item decoration to recyclerview,
     * - adds swipe LEFT to remove IF THE SHOPLIST IS ACTIVE,
     * - adds listener for FAB IF THE SHOPLIST IS ACTIVE,
     * - turns off the FAB IF THE SHOPLIST IS INACTIVE.
     */
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
                addEditNewItem(null, object : OnItemClickListener {
                    override fun onItemClickListener(item: Any) {
                        viewModel.addShopItem(item as ShopItem)
                    }
                })
            }
        } else {
            binding.addItemFAB.visibility = View.GONE
        }
    }

    /**
     * Method which:
     * - adds observer for all ShopItems and passes them to the adapter.
     */
    private fun setObservers() {
        viewModel.items.observe(viewLifecycleOwner, { shopItems ->
            detailsAdapter.submitList(shopItems) {
                binding.listDetailsRecyclerView.scrollToPosition(0)
            }
        })
    }

    /**
     * Method which:
     * - adds swipe to the LEFT to delete recyclerview items.
     */
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

    /**
     * Method which allows to add or edit a ShopItem.
     */
    private fun addEditNewItem(shopItem: ShopItem?, listener: OnItemClickListener) {
        ShopItemDialog(shopItem, shopListId!!, listener).show(
            requireFragmentManager(),
            getString(R.string.provide_item_dialog)
        )
    }

    /**
     * Special listener created to allow archive the ShopItems.
     */
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

    /**
     * Special listener created to allow editing of ShopItems.
     */
    private val longClickListener = object : OnLongClickListener {
        override fun onLongClickListener(item: Any) {
            addEditNewItem(item as ShopItem, object : OnItemClickListener {
                override fun onItemClickListener(item: Any) {
                    viewModel.updateShopItem(item as ShopItem)
                    detailsAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}