package com.sample.qr.ui.activities.main

import android.os.Bundle
import com.sample.qr.R
import com.sample.qr.managers.extensions.setFullscreen
import com.sample.qr.managers.extensions.setStatusBarLight
import com.sample.qr.mvp.screens.FragmentsScreen
import com.sample.qr.ui.activities.base.BaseActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class VolunteerActivity : BaseActivity() {

    companion object {
        val TAG = VolunteerActivity::class.java.simpleName
    }

    override var mNavigator = SupportAppNavigator(this, R.id.frameContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        setStatusBarLight(true)
        setFullscreen(true)

        if (savedInstanceState == null) {
            mRouter.replaceScreen(FragmentsScreen.VolunteerQrReaderScreen())
        }
    }
}
