package com.sample.qr.presentation.ui.screens.volunteer.qr

import com.sample.qr.presentation.ui.screens.base.BaseView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface VolunteerQrReaderView : BaseView {

    fun onProgressBarVisibility(isVisible: Boolean)

    fun onScannerState()

    fun onSuccessState()

    fun onUnknownState()

    fun onActivatedState()

}