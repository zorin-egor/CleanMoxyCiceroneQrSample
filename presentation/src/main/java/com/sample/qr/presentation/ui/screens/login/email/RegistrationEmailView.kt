package com.sample.qr.presentation.ui.screens.login.email

import android.graphics.Bitmap
import com.sample.qr.presentation.ui.screens.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface RegistrationEmailView : BaseView {

    fun onRegistrationButtonState(isEnabled: Boolean, isProgress: Boolean = false)

    fun onCaptchaBitmap(bitmap: Bitmap? = null)

    fun onNameError()

    fun onSurnameError()

    fun onEmailError()

    fun onCaptchaError()

    fun onCaptchaProgress(isVisible: Boolean)

}