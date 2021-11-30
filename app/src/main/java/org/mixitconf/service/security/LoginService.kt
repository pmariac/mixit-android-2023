package org.mixitconf.service.security

import android.accounts.Account
import android.accounts.AccountManager
import android.util.Log
import org.mixitconf.R
import org.mixitconf.api.UserApiRepository
import org.mixitconf.database.UserRepository
import org.mixitconf.model.User
import org.mixitconf.service.InvalidCredentialException
import org.mixitconf.service.Resource
import org.mixitconf.service.UnknownUserException
import org.mixitconf.service.asError

data class LoginResponse(
    val user: User? = null,
    val message: Int? = null
)

class LoginService(
    private val userApiRepository: UserApiRepository,
    private val userRepository: UserRepository,
    private val accountManager: AccountManager
) {

    companion object {
        private const val LOG_TAG = "LoginService"
    }

    /**
     * A method to check user token on MiXiT website .
     * 1. If email is not known, an exception is thrown (user must create his/her account on
     *    MiXiT website.
     * 2. If token is expired a new token is sent to the user by email.
     * 3. if token is valid we try to load the user profile
     */
    private suspend fun getUser(email: String, token: String): Resource<LoginResponse> {
        userApiRepository.checkToken(email, token).apply {
            when (code()) {
                200 -> {
                    userApiRepository.profile(email, token).apply {
                        if (isSuccessful) {
                            body()?.let {
                                val user = it.toUser(email, token)
                                userRepository.insert(user)
                                return Resource.Success(LoginResponse(user))
                            }
                        }
                        return asError()
                    }
                }
                400 -> return reinitializeToken(email)
                else -> return Resource.Error(UnknownUserException())
            }
        }
    }

    /**
     * Reinitialize token
     */
    private suspend fun reinitializeToken(email: String): Resource<LoginResponse> {
        userApiRepository.askForToken(email).apply {
            return when (code()) {
                200 -> Resource.Success(LoginResponse(message = R.string.info_sent_token))
                400 -> Resource.Error(InvalidCredentialException())
                404 -> Resource.Error(UnknownUserException())
                else -> Resource.Error(RuntimeException())
            }
        }
    }

    suspend fun login(email: String, token: String?): Resource<LoginResponse> =
        try {
            if (token != null) getUser(email, token) else reinitializeToken(email)
        } catch (e: Exception) {
            Log.w(LOG_TAG, "Unexpected error on login ", e)
            Resource.Error(e)
        }

    suspend fun logout(): Resource<LoginResponse> {
        userRepository.clearAll()
        accountManager.removeAccount(getUserAccount(), null, null, null)
        return Resource.Success(LoginResponse())
    }

    fun isUserLoggedIn(): Boolean = getUserAccount() != null

    fun getUserEmail(): String? = getUserAccount()?.name


    private fun getUserAccount(): Account? =
        try {
            accountManager.getAccountsByType(Authenticator.ACCOUNT_TYPE).first()
        } catch (e: Exception) {
            null
        }

    fun saveUserAccount(email: String, token: String) {
        Account(email, Authenticator.ACCOUNT_TYPE).also { account ->
            accountManager.addAccountExplicitly(account, token, null)
        }
    }
}