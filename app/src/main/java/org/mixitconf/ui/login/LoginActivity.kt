package org.mixitconf.ui.login

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.mixitconf.R
import org.mixitconf.ui.ActivityWithFragment
import org.mixitconf.ui.BaseActivity

class LoginActivity : BaseActivity() {

    override val params by lazy {
        ActivityWithFragment(R.layout.activity_login, R.id.fl_login_fragment, LoginFragment())
    }

    companion object {
        fun start(context: Context) =
            context.startActivity(
                Intent(context, LoginActivity::class.java).apply {
                    flags = FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_NEW_TASK
                }
            )
    }
}