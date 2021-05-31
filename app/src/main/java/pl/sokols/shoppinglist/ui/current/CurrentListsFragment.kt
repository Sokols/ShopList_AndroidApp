package pl.sokols.shoppinglist.ui.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.shoppinglist.R

@AndroidEntryPoint
class CurrentListsFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentListsFragment()
    }

    private val viewModel: CurrentListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.current_lists_fragment, container, false)
    }

}