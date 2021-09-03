package org.mixitconf.ui.home

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import org.mixitconf.R
import org.mixitconf.ui.BaseActivity
import org.mixitconf.ui.login.LoginActivity

class MainActivity : BaseActivity() {

    override val navController by lazy {
        findNavController(R.id.main_nav_host)
    }
    override val appBarConfiguration by lazy {
        AppBarConfiguration.Builder(R.id.homeFragment).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_link_login.setOnClickListener { LoginActivity.start(this) }
    }
}