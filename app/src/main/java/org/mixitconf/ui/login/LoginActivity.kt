package org.mixitconf.ui.login

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.mixitconf.R
import org.mixitconf.databinding.ActivityLoginBinding
import org.mixitconf.ui.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val navController by lazy {
        findNavController(R.id.main_nav_host)
    }
    override val appBarConfiguration by lazy {
        AppBarConfiguration.Builder(navController.graph).build()
    }
    override val topToolbar: Toolbar? = null
    override val bottomToolbar: BottomNavigationView? = null

    companion object {
        @JvmStatic
        fun start(context: Context) =
            context.startActivity(
                Intent(context, LoginActivity::class.java).apply {
                    flags = FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_NEW_TASK
                }
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityLoginBinding.inflate(layoutInflater).apply {
            viewBinding = this
            setContentView(viewBinding.root)
            supportFragmentManager.commit { replace(R.id.fl_login_fragment, LoginFragment()) }
        }
    }



}