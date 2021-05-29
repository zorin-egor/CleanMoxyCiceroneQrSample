package com.sample.qr.presentation.ui.screens.splash

import android.os.Bundle
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.setFullscreen
import com.sample.qr.presentation.extensions.show
import com.sample.qr.presentation.ui.screens.base.BaseActivity

internal class SplashActivity : BaseActivity() {

    override fun getNavigationId() = R.id.frameContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setFullscreen(true)

        if (savedInstanceState == null) {
//            mRouter.newRootScreen(FragmentsScreen.SplashScreen())
            supportFragmentManager.show(SplashFragment.newInstance(), R.id.frameContainer, false, null)
        }
    }
}
