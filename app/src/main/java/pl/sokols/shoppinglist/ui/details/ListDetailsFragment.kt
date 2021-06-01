package pl.sokols.shoppinglist.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.databinding.ListDetailsFragmentBinding
import pl.sokols.shoppinglist.ui.details.adapters.DetailsAdapter
import pl.sokols.shoppinglist.utils.DividerItemDecorator
import pl.sokols.shoppinglist.utils.OnItemClickListener

@AndroidEntryPoint
class ListDetailsFragment : Fragment() {

    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var binding: ListDetailsFragmentBinding
    private val viewModel: ListDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDetailsFragmentBinding.inflate(inflater, container, false)
        detailsAdapter = DetailsAdapter(onItemClickListener)
        setComponents()
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
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClickListener(item: Any) {
            TODO("Not yet implemented")
        }
    }

    private val onFABClickListener = object : OnItemClickListener {
        override fun onItemClickListener(item: Any) {
            TODO("Not yet implemented")
        }
    }
}