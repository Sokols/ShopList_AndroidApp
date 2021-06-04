package pl.sokols.shoppinglist.ui.current

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
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.data.entities.ShopListDetails
import pl.sokols.shoppinglist.databinding.CurrentListsFragmentBinding
import pl.sokols.shoppinglist.ui.adapters.DividerItemDecorator
import pl.sokols.shoppinglist.ui.adapters.ListsAdapter
import pl.sokols.shoppinglist.utils.*

/**
 * Fragment of current ShopLists.
 */
@AndroidEntryPoint
class CurrentListsFragment : Fragment() {

    private lateinit var listsAdapter: ListsAdapter
    private lateinit var binding: CurrentListsFragmentBinding
    private val viewModel: CurrentListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrentListsFragmentBinding.inflate(inflater, container, false)
        listsAdapter = ListsAdapter(longClickListener)
        setObservers()
        setComponents()
        return binding.root
    }

    /**
     * Method which:
     * - adds adapter to recyclerview,
     * - adds item decoration to recyclerview,
     * - adds swipe RIGHT to unarchive,
     * - adds swipe LEFT to remove,
     * - adds listener for FAB.
     */
    private fun setComponents() {
        binding.currentListsRecyclerView.adapter = listsAdapter
        binding.currentListsRecyclerView.addItemDecoration(
            DividerItemDecorator(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.divider,
                    null
                )!!
            )
        )

        addSwipeToArchive()
        addSwipeToDelete()

        binding.addListFAB.setOnClickListener {
            addEditNewList(null, object : OnItemClickListener {
                override fun onItemClickListener(item: Any) {
                    viewModel.addShopList(item as ShopList)
                }
            })
        }
    }

    /**
     * Method which:
     * - adds observer for all ShopLists and passes them to the adapter.
     */
    private fun setObservers() {
        viewModel.items.observe(viewLifecycleOwner, { shopLists ->
            listsAdapter.submitList(shopLists) {
                binding.currentListsRecyclerView.scrollToPosition(0)
            }
        })
    }

    /**
     * Method which:
     * - adds swipe to the RIGHT to unarchive for recyclerview items,
     * - allows undo the operation.
     */
    private fun addSwipeToArchive() {
        ItemTouchHelper(object : SwipeHelper(ItemTouchHelper.RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val archivedList: ShopList =
                    listsAdapter.currentList[viewHolder.adapterPosition].shopList
                archivedList.isActive = !archivedList.isActive
                viewModel.updateShopList(archivedList)

                Utils.getSnackbar(
                    requireView(),
                    String.format(getString(R.string.archived), archivedList.name),
                    requireActivity()
                ).setAction(getString(R.string.undo)) {
                    archivedList.isActive = !archivedList.isActive
                    viewModel.updateShopList(archivedList)
                }.show()
            }
        }).attachToRecyclerView(binding.currentListsRecyclerView)
    }

    /**
     * Method which:
     * - adds swipe to the LEFT to delete recyclerview items.
     */
    private fun addSwipeToDelete() {
        ItemTouchHelper(object : SwipeHelper(ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedList: ShopList =
                    listsAdapter.currentList[viewHolder.adapterPosition].shopList
                viewModel.deleteShopList(deletedList)

                Utils.getSnackbar(
                    requireView(),
                    String.format(getString(R.string.deleted), deletedList.name),
                    requireActivity()
                ).show()
            }
        }).attachToRecyclerView(binding.currentListsRecyclerView)
    }

    /**
     * Method which allows to add or edit a ShopList.
     */
    private fun addEditNewList(shopList: ShopList?, listener: OnItemClickListener) {
        ShopListDialog(shopList, listener).show(
            requireFragmentManager(),
            getString(R.string.provide_item_dialog)
        )
    }

    /**
     * Special listener created to allow editing of ShopLists.
     */
    private val longClickListener = object : OnLongClickListener {
        override fun onLongClickListener(item: Any) {
            val shopListDetails = item as ShopListDetails
            addEditNewList(shopListDetails.shopList, object : OnItemClickListener {
                override fun onItemClickListener(item: Any) {
                    viewModel.updateShopList(item as ShopList)
                    listsAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}