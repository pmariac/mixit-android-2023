package org.mixitconf.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.mixitconf.MainNavigationGraphDirections
import org.mixitconf.Properties
import org.mixitconf.R
import org.mixitconf.databinding.ActivityMainBinding
import org.mixitconf.ui.BaseActivity
import org.mixitconf.ui.talk.TalksFragment
import org.mixitconf.workers.DataSynchronizationWorker

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun getIntent(context: Context, talkId: Long): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(Properties.TALK_ID, talkId)
            }
        }
    }

    override val navController by lazy {
        findNavController(R.id.main_nav_host)
    }

    override val appBarConfiguration by lazy {
        AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_talks,
            R.id.navigation_speakers,
            R.id.navigation_favorites)
        )
    }
    override val topToolbar: Toolbar by lazy {
        viewBinding.toolbarTop.toolbar
    }
    override val bottomToolbar: BottomNavigationView by lazy {
        viewBinding.toolbarBottom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            viewBinding = this
            setContentView(viewBinding.root)
        }
        enqueueWorkers(this)
    }

    override fun onStart() {
        super.onStart()
        val talkId = intent.getLongExtra(Properties.TALK_ID, 0)
        if (talkId > 0) {
            val directions = MainNavigationGraphDirections.actionTalksFragmentToTalkDetailFragment(talkId)
            navController.navigate(directions)
        }
    }

    private fun enqueueWorkers(context: Context) {
        DataSynchronizationWorker.enqueuePeriodicWorker(context)
    }

}