package org.mixitconf.service

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import org.mixitconf.R
import org.mixitconf.show

/**
 * Class to simplify Resource handling
 */
class ResourceHandler<T : Any>(

    /**
     * The current [Context]
     */
    val context: Context,

    /**
     * Triggered when the handled resource is [Resource.Loading]
     */
    private val progressBar: View? = null,

    /**
     * Triggered when the handled resource is [Resource.Success]
     */
    private val successHandler: (T) -> Unit
) {

    /**
     * Handles the resource
     * @param resource The resource to handle
     */
    fun handle(resource: Resource<T>) {
        when (resource) {
            is Resource.Success -> {
                progressBar?.show(false)
                successHandler.invoke(resource.data)
            }
            is Resource.Loading -> progressBar?.show(true)
            is Resource.Error -> {
                progressBar?.show(false)
                handleException(resource.exception)
            }
        }
    }

    /**
     * Handles the exception. Is only triggered when the view didn't handle the Resource.Error
     */
    private fun handleException(error: Exception) {
        when (error) {
            is NoInternetException -> displayErrorDialog(R.string.error_unknown)
            is UnknownUserException -> displayErrorDialog(R.string.error_unknown_email)
            is ForbiddenException -> displayErrorDialog(R.string.error_forbidden)
            is InvalidCredentialException -> displayErrorDialog(R.string.error_invalid_credential)
            else -> displayErrorDialog(R.string.error_unknown)
        }
    }

    /**
     * Displays a simple dialog to inform the user about an error using a string resource.
     */
    private fun displayErrorDialog(@StringRes errorResource: Int): Boolean {
        AlertDialog.Builder(context)
            .setMessage(context.getString(errorResource))
            .setPositiveButton(android.R.string.ok, null)
            .show()
        return true
    }
}
