package pl.sokols.shoppinglist.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import pl.sokols.shoppinglist.R
import pl.sokols.shoppinglist.data.entities.ShopItem

class Utils {

    companion object {
        const val SHOP_LIST_ID_KEY = "shop_list_id"
        const val SHOP_LIST_IS_ACTIVE_KEY = "shop_list_is_active"

        /**
         * Method which allows to display snackbars above the bottom navigation.
         */
        fun getSnackbar(view: View, message: String, activity: Activity): Snackbar {
            val snackbar = Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = activity.findViewById(R.id.bottomNavigation)
            return snackbar
        }

        /**
         * Method which checks ShopItems which are checked.
         */
        fun getChecked(shopItems: List<ShopItem>): Int {
            var checked = 0
            for (shopItem: ShopItem in shopItems) {
                if (shopItem.isChecked) {
                    checked++
                }
            }
            return checked
        }
    }
}