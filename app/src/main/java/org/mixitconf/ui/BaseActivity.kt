package org.mixitconf.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import kotlinx.android.synthetic.main.toolbar_top.*

data class ActivityWithFragment(
    val activityLayout: Int,
    val fragmentId: Int,
    val fragment: BaseFragment
)

abstract class BaseActivity : AppCompatActivity() {

    abstract val params: ActivityWithFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(params.activityLayout)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.commit {
            replace(params.fragmentId, params.fragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}