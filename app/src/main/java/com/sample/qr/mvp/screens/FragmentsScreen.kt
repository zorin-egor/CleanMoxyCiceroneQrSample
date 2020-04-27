package com.sample.qr.mvp.screens


import androidx.fragment.app.Fragment
import com.sample.qr.ui.fragments.common.HtmlViewerFragment
import com.sample.qr.ui.fragments.login.RegistrationEmailFragment
import com.sample.qr.ui.fragments.login.RegistrationWithFragment
import com.sample.qr.ui.fragments.login.SplashFragment
import com.sample.qr.ui.fragments.participant.ParticipantAboutFestivalFragment
import com.sample.qr.ui.fragments.participant.ParticipantAboutMovementFragment
import com.sample.qr.ui.fragments.participant.ParticipantQrCodeFragment
import com.sample.qr.ui.fragments.volunteer.VolunteerLoginFragment
import com.sample.qr.ui.fragments.volunteer.VolunteerQrReaderFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FragmentsScreen {

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