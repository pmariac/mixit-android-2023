package org.mixitconf.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mixitconf.databinding.FragmentHomeBinding
import org.mixitconf.ui.BaseFragment

class MainFragment : BaseFragment<FragmentHomeBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentHomeBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }
}