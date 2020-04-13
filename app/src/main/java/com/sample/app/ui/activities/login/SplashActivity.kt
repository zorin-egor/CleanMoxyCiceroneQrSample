package com.sample.app.ui.activities.login

import android.os.Bundle
import com.sample.app.R
import com.sample.app.mvp.screens.FragmentsScreen
import com.sample.app.ui.activities.base.BaseActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class SplashActivity : BaseActivity() {

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }

    override var mNavigator = SupportAppNavigator(this, R.id.frameContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setStatusBarColor(android.R.color.transparent)
        setNoLimits(true)

        if (savedInstanceState == null) {
            mRouter.newRootScreen(FragmentsScreen.SplashScreen())
        }
    }
}
