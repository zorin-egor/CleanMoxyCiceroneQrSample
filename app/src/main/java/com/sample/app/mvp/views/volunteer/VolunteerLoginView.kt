package com.sample.app.mvp.views.volunteer

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.app.mvp.views.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface VolunteerLoginView : BaseView {

    fun onButtonEnabled(isEnable: Boolean)

    fun onLoginText(text: String)

    fun onLoginError()

    fun onPasswordError()

}