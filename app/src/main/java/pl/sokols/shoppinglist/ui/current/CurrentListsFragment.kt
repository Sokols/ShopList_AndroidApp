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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.databinding.CurrentListsFragmentBinding
import pl.sokols.shoppinglist.ui.current.adapters.ListsAdapter
import pl.sokols.shoppinglist.utils.DividerItemDecorator
import pl.sokols.shoppinglist.utils.OnItemClickListener
import pl.sokols.shoppinglist.utils.ShopListDialog


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
        listsAdapter = ListsAdapter()
        setComponents()
        setObservers()
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
            addNewList()
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
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val archivedList: ShopList = listsAdapter.currentList[viewHolder.adapterPosition]
                archivedList.isActive = !archivedList.isActive
                viewModel.updateShopList(archivedList)

                Snackbar.make(
                    requireView(),
                    String.format(getString(R.string.archived), archivedList.name),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.undo)) {
                    archivedList.isActive = !archivedList.isActive
                    viewModel.updateShopList(archivedList)
                }.show()
            }
        }).attachToRecyclerView(binding.currentListsRecyclerView)
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
                val deletedList: ShopList =
                    listsAdapter.currentList[viewHolder.adapterPosition]

                viewModel.deleteShopList(deletedList)

                Snackbar.make(
                    requireView(),
                    String.format(getString(R.string.deleted), deletedList.name),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.undo)) {
                        viewModel.addShopList(deletedList)
                    }.show()
            }
        }).attachToRecyclerView(binding.currentListsRecyclerView)
    }

    private fun addNewList() {
        ShopListDialog(object : OnItemClickListener {
            override fun onItemClickListener(item: Any) {
                viewModel.addShopList(item as ShopList)
            }
        }).show(requireFragmentManager(), getString(R.string.provide_item_dialog))
    }
}