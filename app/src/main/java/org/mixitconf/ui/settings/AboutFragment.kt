package org.mixitconf.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about.*
import org.mixitconf.BuildConfig
import org.mixitconf.R
import org.mixitconf.ui.BaseFragment

class AboutFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBuildBumber()
    }

    private fun initBuildBumber() {
        val currentVersion = BuildConfig.VERSION_NAME
        tv_version.text = String.format(getString(R.string.settings_about_version), currentVersion)
    }

}