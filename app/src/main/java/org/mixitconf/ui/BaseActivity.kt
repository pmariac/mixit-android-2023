package org.mixitconf.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.toolbar_top.*
import org.mixitconf.R
import org.mixitconf.ui.home.MainActivity
import org.mixitconf.ui.home.MainFragmentDirections
import org.mixitconf.ui.home.TwitterIntent

abstract class BaseActivity : AppCompatActivity() {

    abstract val navController: NavController
    abstract val appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * We have to wait the activity creation to initialize navigation
     * [https://issuetracker.google.com/issues/142847973]
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            // setLogo(R.drawable.mxt_logo_large)
            //setDisplayUseLogoEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
//        if(this is MainActivity) {
//            finish()
//        }
        if (!navController.navigateUp()) {
            finish()
        }
        return true
    }

    /**
     * Initializes top action bar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.global, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Listener used on top action bar when a user clicks on an action
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        R.id.main_nav_host
        when (item.itemId) {
            R.id.navigation_twitter -> TwitterIntent.start(applicationContext)
            R.id.navigation_settings -> navController.navigate(MainFragmentDirections.actionSettingsFragment())
            R.id.homeFragment -> navController.navigate(R.id.homeFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}