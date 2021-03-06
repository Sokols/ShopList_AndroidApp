package pl.sokols.shoppinglist.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Binding adapter created for manage striking textviews.
 */
@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
    if (strikeThrough) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}