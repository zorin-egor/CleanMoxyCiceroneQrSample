package com.sample.qr.presentation.ui.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.setFullscreen
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseActivity

@SuppressLint("CustomSplashScreen")
internal class SplashActivity : BaseActivity() {

    override fun getNavigationId() = R.id.frameContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setFullscreen(true)

        if (savedInstanceState == null) {
            router.newRootScreen(FragmentsScreen.SplashScreen())
//            supportFragmentManager.show(SplashFragment.newInstance(), R.id.frameContainer, false, null)
        }
    }
}
