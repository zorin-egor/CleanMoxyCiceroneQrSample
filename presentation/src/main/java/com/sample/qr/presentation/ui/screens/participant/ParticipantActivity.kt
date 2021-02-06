package com.sample.qr.presentation.ui.screens.participant

import android.os.Bundle
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.setFullscreen
import com.sample.qr.presentation.extensions.setStatusBarLight
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseActivity

internal class ParticipantActivity : BaseActivity() {

    override fun getNavigationId() = R.id.frameContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participant)
        setStatusBarLight(true)
        setFullscreen(true)

        if (savedInstanceState == null) {
            mRouter.replaceScreen(FragmentsScreen.ParticipantQrScreen())
        }
    }
}
