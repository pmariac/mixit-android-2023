package org.mixitconf.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private var _binding: T? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun setViewBinding(viewBinding: T) {
        _binding = viewBinding
    }

    protected val viewBinding: T by lazy { _binding!! }
}