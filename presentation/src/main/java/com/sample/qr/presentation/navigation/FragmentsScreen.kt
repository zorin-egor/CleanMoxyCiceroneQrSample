package com.sample.qr.presentation.navigation

import androidx.fragment.app.Fragment
import com.sample.qr.presentation.ui.screens.common.HtmlViewerFragment
import com.sample.qr.presentation.ui.screens.login.email.RegistrationEmailFragment
import com.sample.qr.presentation.ui.screens.login.with.RegistrationWithFragment
import com.sample.qr.presentation.ui.screens.participant.festival.ParticipantAboutFestivalFragment
import com.sample.qr.presentation.ui.screens.participant.movement.ParticipantAboutMovementFragment
import com.sample.qr.presentation.ui.screens.participant.qr.ParticipantQrCodeFragment
import com.sample.qr.presentation.ui.screens.splash.SplashFragment
import com.sample.qr.presentation.ui.screens.volunteer.login.VolunteerLoginFragment
import com.sample.qr.presentation.ui.screens.volunteer.qr.VolunteerQrReaderFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

internal class FragmentsScreen {

    class SplashScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SplashFragment.newInstance()
        }
    }

    class AgreementScreen(val url: String) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return HtmlViewerFragment.newUrlInstance(url)
        }
    }

    class RegistrationWithScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return RegistrationWithFragment.newInstance()
        }
    }

    class RegistrationEmailScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return RegistrationEmailFragment.newInstance()
        }
    }

    class ParticipantQrScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ParticipantQrCodeFragment.newInstance()
        }
    }

    class ParticipantAboutFestivalScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ParticipantAboutFestivalFragment.newInstance()
        }
    }

    class ParticipantAboutMovementScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ParticipantAboutMovementFragment.newInstance()
        }
    }

    class VolunteerLoginScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return VolunteerLoginFragment.newInstance()
        }
    }

    class VolunteerQrReaderScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return VolunteerQrReaderFragment.newInstance()
        }
    }

}