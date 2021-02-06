package com.sample.qr.presentation.ui.screens.participant.qr

import android.graphics.Bitmap
import com.sample.qr.presentation.ui.screens.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ParticipantQrCodeView : BaseView {

    fun onProgressVisibility(isVisible: Boolean)

    fun onQrCodeReady(bitmap: Bitmap)

    fun onParticipantName(name: String)

}