package com.sample.qr.mvp.views.login

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.qr.mvp.views.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RegistrationWithView : BaseView {

    fun onAgreementCheckBox(isChecked: Boolean)

    fun onEmailButtonEnable(isEnabled: Boolean)

}