package pl.sokols.shoppinglist.ui.archived

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.shoppinglist.R

@AndroidEntryPoint
class ArchivedListsFragment : Fragment() {

    private val viewModel: ArchivedListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.archived_lists_fragment, container, false)
    }


}