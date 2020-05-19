package com.sample.qr.mvp.views.login

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.qr.mvp.views.base.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface SplashView : BaseView