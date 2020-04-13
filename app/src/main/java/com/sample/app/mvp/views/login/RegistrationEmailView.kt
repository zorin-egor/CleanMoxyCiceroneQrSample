package com.sample.app.mvp.views.login

import android.graphics.Bitmap
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.app.mvp.views.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RegistrationEmailView : BaseView {

    fun onRegistrationButtonState(isEnabled: Boolean, isProgress: Boolean = false)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onCaptchaBitmap(bitmap: Bitmap?)

    fun onNameError()

    fun onSurnameError()

    fun onEmailError()

    fun onCaptchaError()

    fun onCaptchaProgress(isVisible: Boolean)

}