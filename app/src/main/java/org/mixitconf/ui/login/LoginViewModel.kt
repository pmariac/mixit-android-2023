package org.mixitconf.ui.login

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.mixitconf.R
import org.mixitconf.service.Resource
import org.mixitconf.service.security.LoginResponse
import org.mixitconf.service.security.LoginService
import java.util.regex.Pattern

class LoginViewModel(private val loginService: LoginService) : ViewModel() {
    /**
     * Form state can be updated but we don't want to expose a mutable livedata
     */
    private val _loginFormState = MutableLiveData<LoginFormState>()

    /**
     * Exposed live data is not mutable
     */
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    /**
     * Check if email and token are ok
     */
    fun loginDataChanged(email: String?, token: String?, tokenRequired: Boolean = true) {
        // Email and password errors
        val emailError = if (!isValidEmail(email)) R.string.error_invalid_email else null
        val tokenError =
            if (!isTokenValid(token) && tokenRequired) R.string.error_invalid_token else null

        _loginFormState.value = LoginFormState(
            emailError = emailError,
            tokenError = tokenError,
            isDataValid = emailError == null && tokenError == null
        )
    }

    fun login(email: String, token: String): LiveData<Resource<LoginResponse>>? {
        // Validate data
        loginDataChanged(email, token)

        // Return LiveData to be observed when data is valid
        if (loginFormState.value?.isDataValid == true) {
            return liveData {
                emit(Resource.Loading())
                val result = loginService.login(email, token)
                if (result is Resource.Success) {
                    loginService.saveUserAccount(email, token)
                }
                emit(result)
            }
        }
        // Return null when data is invalid
        return null
    }

    private fun isValidEmail(email: String?): Boolean =
        email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isTokenValid(password: String?): Boolean =
        password != null
                && Pattern.compile("[a-zA-Z0-9]+").matcher(password).matches()
                && password.length > 10
}

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    @StringRes val emailError: Int? = null,
    @StringRes val tokenError: Int? = null,
    val isDataValid: Boolean = false
)