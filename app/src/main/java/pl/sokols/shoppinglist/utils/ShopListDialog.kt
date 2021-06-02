package pl.sokols.shoppinglist.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopList
import pl.sokols.shoppinglist.databinding.ShopListDialogBinding

class ShopListDialog(
    private val listener: OnItemClickListener?
) : DialogFragment() {

    private var shopList: ShopList = ShopList("")
    private lateinit var dialogBinding: ShopListDialogBinding

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = ShopListDialogBinding.inflate(inflater, container, false)
        dialogBinding.shopList = shopList
        setComponents()
        return dialogBinding.root
    }

    private fun setComponents() {
        dialogBinding.applyListDialogButton.setOnClickListener {
            if (dialogBinding.listNameEditText.text.toString().isEmpty()) {
                dialogBinding.listNameTextInputLayout.error = getString(R.string.incorrect_value)
                dialogBinding.listNameTextInputLayout.isErrorEnabled = true
            } else {
                dialogBinding.listNameTextInputLayout.isErrorEnabled = false
            }

            if (shopList.name.isNotEmpty()) {
                listener!!.onItemClickListener(shopList)
                dismiss()
            }
        }
    }
}