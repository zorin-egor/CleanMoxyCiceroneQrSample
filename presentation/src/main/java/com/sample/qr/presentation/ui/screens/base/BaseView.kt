package com.sample.qr.presentation.ui.screens.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface BaseView : MvpView {

    fun onMessage(value: String)

}