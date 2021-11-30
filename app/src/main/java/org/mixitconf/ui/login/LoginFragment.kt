package org.mixitconf.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.loginFormState.observe(viewLifecycleOwner, { loginState ->
            viewBinding.textErrorEmail.show(loginState?.emailError != null)
            viewBinding.textErrorToken.show(loginState?.tokenError != null)
        })
    }

    private fun login() {
        viewBinding.editTextEmail.hideKeyboard()
        viewBinding.editTextToken.hideKeyboard()

        val email = viewBinding.editTextEmail.text?.toString()?.trim() ?: ""
        val password = viewBinding.editTextToken.text?.toString()?.trim() ?: ""

        viewModel.login(email, password)?.observe(viewLifecycleOwner, { result ->
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