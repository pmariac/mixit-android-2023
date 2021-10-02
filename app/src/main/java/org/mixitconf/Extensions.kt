package org.mixitconf

import android.content.Context
import android.content.pm.PackageManager
import android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
import android.text.Html.fromHtml
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import org.mixitconf.Properties.SPECIAL_SLUG_CHARACTERS
import org.mixitconf.model.entity.Speaker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
    visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Extension function to test if an intent package is available
 */
fun Context.hasIntentPackage(type: String): Boolean {
    try {
        packageManager.getPackageInfo(type, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
    return true
}

fun Context.hasPermission(permission: String) =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

/**
 * Extension fonction to transform a Date in a french time
 */
val Date.toFrenchTime
    get(): Date {
        val calendar = Calendar.getInstance(Locale.FRANCE)
        calendar.time = this
        calendar.add(Calendar.HOUR, -2)
        return calendar.time
    }

fun Date.toString(pattern: String): String =
    SimpleDateFormat(pattern).format(this)

/**
 * Format date in user locale
 */
fun Date.formatToString(format: String = "EEE"): String =
    SimpleDateFormat(format, Locale.getDefault()).format(this)

/**
 * Format time in user locale
 */
fun Date.formatTimeToString(format: Int = DateFormat.SHORT): String =
    DateFormat.getTimeInstance(format).format(this)

/**
 * Add minutes to a date
 */
fun Date.addMinutes(amount: Int): Date {
    val calendar = Calendar.getInstance(Locale.FRANCE)
    calendar.time = this
    calendar.add(Calendar.MINUTE, amount)
    return calendar.time
}

/**
 * String extension to convert markdown to HTML
 */
val String?.toHtml: String
    get() = if (this == null) "" else fromHtml(this, TO_HTML_PARAGRAPH_LINES_CONSECUTIVE).toString()


/**
 * String extension to remove special characters
 */
val String?.toSlug: String
    get() = this
        ?.lowercase(Locale.getDefault())
        ?.toCharArray()
        ?.map { SPECIAL_SLUG_CHARACTERS[it] ?: it }
        ?.joinToString("") ?: ""

/**
 * Populate an image view with the expected resource or use default icon
 */
fun ImageView.setSpeakerImage(speaker: Speaker) {
    // Speaker images are downloaded on the app startup
    val imageResource = context.resources.getIdentifier(
        speaker.login.toSlug, "drawable", context.applicationInfo.packageName
    )
    //val size = this.resources.getDimension(R.dimen.speaker_image_side).toInt()
    Picasso.get()
        .load(if (imageResource > 0) imageResource else R.drawable.mxt_icon_unknown)
        .resize(160, 160)
        .into(this)
}





