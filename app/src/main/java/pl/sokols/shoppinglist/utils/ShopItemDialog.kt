package pl.sokols.shoppinglist.utils

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.databinding.ShopItemDialogBinding


class ShopItemDialog(
    private val shopListId: Int,
    private val listener: OnItemClickListener
) : DialogFragment() {

    var shopItem: ShopItem = ShopItem(name = "", shopListId = shopListId, amount = 0)

    private lateinit var dialogBinding: ShopItemDialogBinding

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = ShopItemDialogBinding.inflate(inflater, container, false)
        dialogBinding.shopItem = shopItem
        setComponents()
        return dialogBinding.root
    }

    private fun setComponents() {
        dialogBinding.applyDialogButton.setOnClickListener {
            println(shopItem)
            if (shopItem.name!!.isEmpty()) {
                dialogBinding.itemNameTextInputLayout.error = getString(R.string.incorrect_value)
                dialogBinding.itemNameTextInputLayout.isErrorEnabled = true
            } else {
                dialogBinding.itemNameTextInputLayout.isErrorEnabled = false
            }
            if (shopItem.amount == 0) {
                dialogBinding.itemAmountTextInputLayout.error = getString(R.string.incorrect_value)
                dialogBinding.itemAmountTextInputLayout.isErrorEnabled = true
            } else {
                dialogBinding.itemAmountTextInputLayout.isErrorEnabled = false
            }
            if (shopItem.name != null && shopItem.amount != null && shopItem.name!!.isNotEmpty() && shopItem.amount != 0) {
                listener.onItemClickListener(shopItem)
                dismiss()
            }
        }
    }
}