package com.sample.qr.presentation.ui.screens.volunteer.login

import com.sample.qr.presentation.ui.screens.base.BaseView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface VolunteerLoginView : BaseView {

    fun onButtonEnabled(isEnable: Boolean)

    fun onLoginText(text: String)

    fun onLoginError()

    fun onPasswordError()

}