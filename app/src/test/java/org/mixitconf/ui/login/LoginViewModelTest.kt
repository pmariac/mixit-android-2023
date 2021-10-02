//package org.mixitconf.ui.login
//
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import io.mockk.mockk
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mixitconf.BaseTest
//import org.mixitconf.R
//
//@RunWith(AndroidJUnit4::class)
//class LoginViewModelTest : BaseTest() {
//    private lateinit var model: LoginViewModel
//
//    @Before
//    fun setUp() {
//        model = LoginViewModel(mockk())
//    }
//
//    private fun checkError(emailError: Boolean = false, tokenError: Boolean= false) {
//        val inError = emailError || tokenError
//        val state = model.loginFormState.value
//        assertEquals(state?.isDataValid, !inError)
//        assertEquals(state?.emailError, if(emailError) R.string.error_invalid_email else null)
//        assertEquals(state?.tokenError, if(tokenError) R.string.error_invalid_token else null)
//    }
//
//    @Test
//    fun `email and token should be valid`() {
//        assertNull(model.loginFormState.value)
//        model.loginDataChanged("guillaume@dev-mind.fr", "frezeedze3445534")
//        checkError()
//    }
//
//    @Test
//    fun `email should be invalid`() {
//        assertNull(model.loginFormState.value)
//        model.loginDataChanged(null, "frezeedze3445534")
//        checkError(emailError = true)
//
//        model.loginDataChanged("guillaume", "frezeedze3445534")
//        checkError(emailError = true)
//
//        model.loginDataChanged("guillaume@", "frezeedze3445534")
//        checkError(emailError = true)
//
//        model.loginDataChanged("guillaume@dev-mind", "frezeedze3445534")
//        checkError(emailError = true)
//    }
//
//    @Test
//    fun `token should be invalid`() {
//        assertNull(model.loginFormState.value)
//
//        // Not null
//        model.loginDataChanged("guillaume@dev-mind.fr", null)
//        checkError(tokenError = true)
//
//        // Minimum length
//        model.loginDataChanged("guillaume@dev-mind.fr", "frezee")
//        checkError(tokenError = true)
//
//        // No special characters
//        model.loginDataChanged("guillaume@dev-mind.fr", "freze?>edze3445534")
//        checkError(tokenError = true)
//
//    }
//}