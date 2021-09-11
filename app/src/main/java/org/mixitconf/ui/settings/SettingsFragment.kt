package org.mixitconf.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import org.mixitconf.R
import org.mixitconf.service.AppPreferences

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    /**
     * When user click on "about" line we want to open a fragment with about page
     */
    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            KEY_ABOUT -> launchAboutFragment()
            KEY_ABOUT_MIXIT -> launchMixitAboutPage()
            else -> return super.onPreferenceTreeClick(preference)
        }
        return true
    }

    private fun launchMixitAboutPage() {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://mixitconf.org/schedule")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun launchAboutFragment() {
        val directions = SettingsFragmentDirections.actionOpenAbout()
        findNavController().navigate(directions)
    }

    companion object {
        const val KEY_ABOUT = "about_app"
        const val KEY_ABOUT_MIXIT = "about_app_mixit"
    }
}