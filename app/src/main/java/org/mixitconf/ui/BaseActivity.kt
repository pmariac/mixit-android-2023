package org.mixitconf.ui

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.mixitconf.R
import org.mixitconf.hasPermission
import org.mixitconf.ui.home.MainFragmentDirections
import org.mixitconf.ui.home.TwitterIntent
import org.mixitconf.workers.DataManualSynchronizationWorker

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    abstract val navController: NavController
    abstract val appBarConfiguration: AppBarConfiguration
    abstract val topToolbar: Toolbar?
    abstract val bottomToolbar: BottomNavigationView?
    protected lateinit var viewBinding: T

    /**
     * We have to wait the activity creation to initialize navigation
     * [https://issuetracker.google.com/issues/142847973]
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (topToolbar != null) {
            setSupportActionBar(topToolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
        if(bottomToolbar != null) {
            (bottomToolbar as BottomNavigationView).setupWithNavController(navController)
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
            R.id.navigation_synchronize -> {
                if (applicationContext.hasPermission(Manifest.permission.INTERNET)) {
                    DataManualSynchronizationWorker.enqueueManualWorker(applicationContext)
                    Toast.makeText(applicationContext, R.string.info_sync_start, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, R.string.error_no_internet_permission, Toast.LENGTH_LONG).show()
                }
            }
            R.id.navigation_home -> navController.navigate(R.id.navigation_home)
            R.id.navigation_speakers -> navController.navigate(R.id.navigation_speakers)
            R.id.navigation_onair -> navController.navigate(R.id.navigation_onair)
        }
        return super.onOptionsItemSelected(item)
    }

}