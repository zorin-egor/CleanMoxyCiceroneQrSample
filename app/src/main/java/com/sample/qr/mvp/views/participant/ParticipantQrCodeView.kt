package com.sample.qr.mvp.views.participant

import android.graphics.Bitmap
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.qr.mvp.views.base.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ParticipantQrCodeView : BaseView {

    fun onProgressVisibility(isVisible: Boolean)

    fun onQrCodeReady(bitmap: Bitmap)

    fun onParticipantName(name: String)

}