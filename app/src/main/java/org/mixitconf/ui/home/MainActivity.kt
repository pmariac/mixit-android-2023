package org.mixitconf.ui.home

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import org.mixitconf.R
import org.mixitconf.databinding.ActivityMainBinding
import org.mixitconf.ui.BaseActivity
import org.mixitconf.workers.DataSynchronizationWorker

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val navController by lazy {
        findNavController(R.id.main_nav_host)
    }
    override val appBarConfiguration by lazy {
        AppBarConfiguration.Builder(R.id.homeFragment).build()
    }
    override val toolbar: Toolbar by lazy {
        viewBinding.toolbarTop.toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            viewBinding = this
            setContentView(viewBinding.root)
        }
        enqueueWorkers(this)
    }

    private fun enqueueWorkers(context: Context) {
        DataSynchronizationWorker.enqueuePeriodicWorker(context)
    }


}