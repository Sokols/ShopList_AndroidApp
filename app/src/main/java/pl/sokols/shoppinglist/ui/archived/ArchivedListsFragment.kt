package pl.sokols.shoppinglist.ui.archived

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
import pl.sokols.shoppinglist.databinding.ArchivedListsFragmentBinding
import pl.sokols.shoppinglist.ui.adapters.ListsAdapter
import pl.sokols.shoppinglist.ui.adapters.DividerItemDecorator
import pl.sokols.shoppinglist.utils.SwipeHelper
import pl.sokols.shoppinglist.utils.Utils

@AndroidEntryPoint
class ArchivedListsFragment : Fragment() {

    private lateinit var listsAdapter: ListsAdapter
    private lateinit var binding: ArchivedListsFragmentBinding
    private val viewModel: ArchivedListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArchivedListsFragmentBinding.inflate(inflater, container, false)
        listsAdapter = ListsAdapter(null)
        setComponents()
        setObservers()
        return binding.root
    }

    private fun setComponents() {
        binding.archivedListsRecyclerView.adapter = listsAdapter
        binding.archivedListsRecyclerView.addItemDecoration(
            DividerItemDecorator(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.divider,
                    null
                )!!
            )
        )

        addSwipeToUnarchive()
        addSwipeToDelete()
    }

    private fun setObservers() {
        viewModel.items.observe(viewLifecycleOwner, { shopList ->
            listsAdapter.submitList(shopList) {
                binding.archivedListsRecyclerView.scrollToPosition(0)
            }
        })
    }

    private fun addSwipeToUnarchive() {
        ItemTouchHelper(object : SwipeHelper(ItemTouchHelper.RIGHT) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val archivedList: ShopList = listsAdapter.currentList[viewHolder.adapterPosition].shopList
                archivedList.isActive = !archivedList.isActive
                viewModel.updateShopList(archivedList)

                Utils.getSnackbar(
                    requireView(),
                    String.format(getString(R.string.dearchived), archivedList.name),
                    requireActivity()
                ).setAction(getString(R.string.undo)) {
                    archivedList.isActive = !archivedList.isActive
                    viewModel.updateShopList(archivedList)
                }.show()
            }
        }).attachToRecyclerView(binding.archivedListsRecyclerView)
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
        }).attachToRecyclerView(binding.archivedListsRecyclerView)
    }
}