/*
 * Copyright 2019 Fairphone B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mixitconf.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mixitconf.databinding.FragmentLoginBinding
import org.mixitconf.hideKeyboard
import org.mixitconf.service.Resource
import org.mixitconf.service.ResourceHandler
import org.mixitconf.service.security.LoginResponse
import org.mixitconf.show
import org.mixitconf.ui.BaseFragment

/**
 * User that allows the user to input his/her Zendesk credentials and log in the app.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentLoginBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginFields()
    }

    private fun initLoginFields() {
        // Handle clicks on "Sign in" button
        viewBinding.buttonSignIn.setOnClickListener { login() }

        // Handle clicks on "Forgot token" checkbox
        viewBinding.checkboxForgotToken.setOnClickListener {
            viewBinding.editTextToken.show(!viewBinding.checkboxForgotToken.isChecked)
        }

        // Handle errors
        viewBinding.progressBar.show(false)
        viewBinding.textErrorEmail.show(false)
        viewBinding.textErrorToken.show(false)
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginState ->
            viewBinding.textErrorEmail.show(loginState?.emailError != null)
            viewBinding.textErrorToken.show(loginState?.tokenError != null)
        })
    }

    private fun login() {
        viewBinding.editTextEmail.hideKeyboard()
        viewBinding.editTextToken.hideKeyboard()

        val email = viewBinding.editTextEmail.text?.toString()?.trim() ?: ""
        val password = viewBinding.editTextToken.text?.toString()?.trim() ?: ""

        viewModel.login(email, password)?.observe(viewLifecycleOwner, Observer { result ->
            // Show/hide loading:
            viewBinding.progressBar.show(result is Resource.Loading)
            val handler = ResourceHandler<LoginResponse>(requireContext(), viewBinding.progressBar) {
                requireActivity().finish()
            }
            // Handle the resource:
            handler.handle(result)
        })
    }

}