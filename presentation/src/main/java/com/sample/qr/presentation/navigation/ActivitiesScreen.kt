package com.sample.qr.presentation.navigation

import android.content.Context
import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.sample.qr.presentation.ui.screens.login.RegistrationActivity
import com.sample.qr.presentation.ui.screens.participant.ParticipantActivity
import com.sample.qr.presentation.ui.screens.splash.SplashActivity
import com.sample.qr.presentation.ui.screens.volunteer.VolunteerActivity

internal class ActivitiesScreen {

    class SplashScreen : ActivityScreen {
        override fun createIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    class RegistrationScreen : ActivityScreen {
        override fun createIntent(context: Context): Intent {
            return Intent(context, RegistrationActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }

    class ParticipantScreen : ActivityScreen {
        override fun createIntent(context: Context): Intent {
            return Intent(context, ParticipantActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }

    class VolunteerScreen : ActivityScreen {
        override fun createIntent(context: Context): Intent {
            return Intent(context, VolunteerActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }

}