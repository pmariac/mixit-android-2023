package org.mixitconf

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Extension function to hide the Keyboard.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension function to show/hide a [View].
 */
fun View.show(show: Boolean) {
    visibility = if(show) View.VISIBLE else View.GONE
}