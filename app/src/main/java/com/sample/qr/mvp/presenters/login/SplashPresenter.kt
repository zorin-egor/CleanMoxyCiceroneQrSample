package com.sample.qr.mvp.presenters.login

import com.arellomobile.mvp.InjectViewState
import com.sample.qr.App
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.screens.ActivitiesScreen
import com.sample.qr.mvp.views.login.SplashView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@InjectViewState
class SplashPresenter : BasePresenter<SplashView>() {

    companion object {
        val TAG = SplashPresenter::class.java.simpleName
    }

    init {
        App.instance.getAppComponent().inject(this)
    }

    fun init() {
        mRoutinesCommon.run({ foreground, instance ->
            delay(2000)

            foreground.launch {
                mRouter.newRootScreen(ActivitiesScreen.RegistrationScreen())
            }
        })
    }

}