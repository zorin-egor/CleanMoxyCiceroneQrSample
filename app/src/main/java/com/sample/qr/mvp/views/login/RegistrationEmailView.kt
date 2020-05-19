package com.sample.qr.mvp.views.login

import android.graphics.Bitmap
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.qr.mvp.views.base.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegistrationEmailView : BaseView {

    fun onRegistrationButtonState(isEnabled: Boolean, isProgress: Boolean = false)

    fun onCaptchaBitmap(bitmap: Bitmap?)

    fun onNameError()

    fun onSurnameError()

    fun onEmailError()

    fun onCaptchaError()

    fun onCaptchaProgress(isVisible: Boolean)

}