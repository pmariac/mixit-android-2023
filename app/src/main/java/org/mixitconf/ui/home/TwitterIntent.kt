package org.mixitconf.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.mixitconf.hasIntentPackage

class TwitterIntent {

    companion object {

        private fun createIntent(context: Context): Intent {
            val hasTwitterApp = context.hasIntentPackage("com.twitter.android")
            val intentUri =
                if (hasTwitterApp) "twitter://user?screen_name=mixitconf" else "https://twitter.com/mixitconf"
            return Intent(
                Intent.ACTION_VIEW,
                Uri.parse(intentUri)
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        @JvmStatic
        fun start(context: Context) = context.startActivity(createIntent(context))
    }
}