package com.sample.app.ui.activities.main

import android.os.Bundle
import com.sample.app.R
import com.sample.app.mvp.screens.FragmentsScreen
import com.sample.app.ui.activities.base.BaseActivity
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
