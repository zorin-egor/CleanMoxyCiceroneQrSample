package com.sample.app.mvp.views.volunteer

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.app.mvp.views.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface VolunteerQrReaderView : BaseView {

    fun onProgressBarVisibility(isVisible: Boolean)

    fun onScannerState()

    fun onSuccessState()

    fun onUnknownState()

    fun onActivatedState()

}