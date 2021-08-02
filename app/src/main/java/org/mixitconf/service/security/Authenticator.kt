package org.mixitconf.service.security

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mixitconf.App
import org.mixitconf.R
import org.mixitconf.api.UserApiRepository

class AuthenticatorService : Service() {
    override fun onBind(intent: Intent?): IBinder? = Authenticator(this).iBinder
}

class Authenticator(private val context: Context) :
    AbstractAccountAuthenticator(context), KoinComponent {

    private val authService: UserApiRepository by inject()

    companion object {
        val ACCOUNT_TYPE: String = App.instance.getString(R.string.account_type)
    }

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        throw UnsupportedOperationException()
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        TODO("Not yet implemented")
    }
}