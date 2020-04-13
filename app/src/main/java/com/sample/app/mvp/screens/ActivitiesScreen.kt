package com.sample.app.mvp.screens


import android.content.Context
import android.content.Intent
import com.sample.app.ui.activities.login.RegistrationActivity
import com.sample.app.ui.activities.login.SplashActivity
import com.sample.app.ui.activities.main.ParticipantActivity
import com.sample.app.ui.activities.main.VolunteerActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ActivitiesScreen {

    class SplashScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    class RegistrationScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, RegistrationActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }

    class ParticipantScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, ParticipantActivity::class.java)
        }
    }

    class VolunteerScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, VolunteerActivity::class.java)
        }
    }

}