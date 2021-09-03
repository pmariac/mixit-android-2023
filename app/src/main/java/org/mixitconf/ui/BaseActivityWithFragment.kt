package org.mixitconf.ui

import android.os.Bundle
import androidx.fragment.app.commit

data class ActivityWithFragmentParams(
    val activityLayout: Int,
    val fragmentId: Int,
    val fragment: BaseFragment
)

abstract class BaseActivityWithFragment : BaseActivity() {

    abstract val params: ActivityWithFragmentParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(params.activityLayout)
        supportFragmentManager.commit {
            replace(params.fragmentId, params.fragment)
        }
    }
}