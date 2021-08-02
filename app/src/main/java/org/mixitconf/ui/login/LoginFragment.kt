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
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mixitconf.R
import org.mixitconf.hideKeyboard
import org.mixitconf.service.Resource
import org.mixitconf.service.ResourceHandler
import org.mixitconf.service.security.LoginResponse
import org.mixitconf.show
import org.mixitconf.ui.BaseFragment

/**
 * User that allows the user to input his/her Zendesk credentials and log in the app.
 */
class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginFields()
    }

    private fun initLoginFields() {
        // Handle clicks on "Sign in" button
        buttonSignIn.setOnClickListener { login() }

        // Handle clicks on "Forgot token" checkbox
        checkboxForgotToken.setOnClickListener {
            editTextToken.show(!checkboxForgotToken.isChecked)
        }

        // Handle errors
        progressBar.show(false)
        textErrorEmail.show(false)
        textErrorToken.show(false)
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginState ->
            textErrorEmail.show(loginState?.emailError !=null)
            textErrorToken.show(loginState?.tokenError !=null)
        })
    }

    private fun login() {
        editTextEmail.hideKeyboard()
        editTextToken.hideKeyboard()

        val email = editTextEmail.text?.toString()?.trim() ?: ""
        val password = editTextToken.text?.toString()?.trim() ?: ""

        viewModel.login(email, password)?.observe(viewLifecycleOwner, Observer { result ->
            // Show/hide loading:
            progressBar.show(result is Resource.Loading)
            val handler = ResourceHandler<LoginResponse>(requireContext(), progressBar){
                requireActivity().finish()
            }
            // Handle the resource:
            handler.handle(result)
        })
    }

}