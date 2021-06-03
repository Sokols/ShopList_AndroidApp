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
            addNewList(null , object : OnItemClickListener {
                override fun onItemClickListener(item: Any) {
                    viewModel.addShopList(item as ShopList)
                }
            })
        }
    }

    private fun setObservers() {
        viewModel.items.observe(viewLifecycleOwner, { shopList ->
            listsAdapter.submitList(shopList) {
                binding.currentListsRecyclerView.scrollToPosition(0)
            }
        })
    }

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

    private fun addNewList(shopList: ShopList?, listener: OnItemClickListener) {
        ShopListDialog(shopList, listener).show(
            requireFragmentManager(),
            getString(R.string.provide_item_dialog)
        )
    }

    private val longClickListener = object : OnLongClickListener {
        override fun onLongClickListener(item: Any) {
            val shopListDetails = item as ShopListDetails
            addNewList(shopListDetails.shopList, object : OnItemClickListener {
                override fun onItemClickListener(item: Any) {
                    viewModel.updateShopList(item as ShopList)
                    listsAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}