package com.sample.app.mvp.views.participant

import android.graphics.Bitmap
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.app.mvp.views.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface ParticipantQrCodeView : BaseView {

    fun onProgressVisibility(isVisible: Boolean)

    fun onQrCodeReady(bitmap: Bitmap)

    fun onParticipantName(name: String)

}