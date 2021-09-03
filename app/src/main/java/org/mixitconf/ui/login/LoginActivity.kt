package org.mixitconf.ui.login

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import org.mixitconf.R
import org.mixitconf.ui.ActivityWithFragmentParams
import org.mixitconf.ui.BaseActivityWithFragment

class LoginActivity : BaseActivityWithFragment() {

    companion object {
        @JvmStatic
        fun start(context: Context) =
            context.startActivity(
                Intent(context, LoginActivity::class.java).apply {
                    flags = FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_NEW_TASK
                }
            )
    }

    override val params by lazy {
        ActivityWithFragmentParams(R.layout.activity_login, R.id.fl_login_fragment, LoginFragment())
    }

    override val navController by lazy {
        findNavController(R.id.main_nav_host)
    }
    override val appBarConfiguration by lazy {
        AppBarConfiguration.Builder(navController.graph).build()
    }

}