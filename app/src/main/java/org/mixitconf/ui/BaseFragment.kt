package org.mixitconf.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    fun baseActivity(): BaseActivity = requireActivity() as BaseActivity
}