package pl.sokols.shoppinglist.ui.current

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
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
        listsAdapter = ListsAdapter(onItemClickListener)
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

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClickListener(item: Any) {
//            TODO("Not yet implemented")
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
        val input = EditText(requireContext())
        input.hint = getString(R.string.name)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setPadding(resources.getDimension(R.dimen.image_padding).toInt())

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.provide_list_name))
            .setView(input)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                if (input.text.isNotEmpty()) {
                    viewModel.addShopList(
                        ShopList(input.text.toString())
                    )
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }
}