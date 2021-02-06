package com.sample.qr.presentation.ui.screens.login.with

import com.sample.qr.presentation.ui.screens.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface RegistrationWithView : BaseView {

    fun onAgreementCheckBox(isChecked: Boolean)

    fun onEmailButtonEnable(isEnabled: Boolean)

}