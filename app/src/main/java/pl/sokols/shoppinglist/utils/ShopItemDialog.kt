package pl.sokols.shoppinglist.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopItem
import pl.sokols.shoppinglist.databinding.ShopItemDialogBinding

/**
 * Helper class created to prepare dialog for ShopItems.
 */
class ShopItemDialog(
    sentShopItem: ShopItem?,
    shopListId: Int,
    private val listener: OnItemClickListener
) : DialogFragment() {

    var shopItem: ShopItem =
        sentShopItem ?: ShopItem(name = "", shopListId = shopListId, amount = 1)

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

    /**
     * Method which:
     * - validates the inputs,
     * - displays the errors if the inputs are incorrect,
     * - turns on the listener if the inputs are correct.
     */
    private fun setComponents() {
        dialogBinding.applyDialogButton.setOnClickListener {
            if (shopItem.name.trim().isEmpty()) {
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
            if (shopItem.name.trim().isNotEmpty() && shopItem.amount != 0) {
                listener.onItemClickListener(shopItem)
                dismiss()
            }
        }
    }
}
