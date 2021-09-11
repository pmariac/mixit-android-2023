package org.mixitconf.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewbinding.ViewBinding
import org.mixitconf.R
import org.mixitconf.ui.home.MainFragmentDirections
import org.mixitconf.ui.home.TwitterIntent

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    abstract val navController: NavController
    abstract val appBarConfiguration: AppBarConfiguration
    abstract val toolbar: Toolbar?
    protected lateinit var viewBinding: T

    /**
     * We have to wait the activity creation to initialize navigation
     * [https://issuetracker.google.com/issues/142847973]
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
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