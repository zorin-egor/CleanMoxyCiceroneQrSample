package com.sample.qr.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.sample.qr.presentation.ui.screens.common.HtmlViewerFragment
import com.sample.qr.presentation.ui.screens.login.email.RegistrationEmailFragment
import com.sample.qr.presentation.ui.screens.login.with.RegistrationWithFragment
import com.sample.qr.presentation.ui.screens.participant.festival.ParticipantAboutFestivalFragment
import com.sample.qr.presentation.ui.screens.participant.movement.ParticipantAboutMovementFragment
import com.sample.qr.presentation.ui.screens.participant.qr.ParticipantQrCodeFragment
import com.sample.qr.presentation.ui.screens.splash.SplashFragment
import com.sample.qr.presentation.ui.screens.volunteer.login.VolunteerLoginFragment
import com.sample.qr.presentation.ui.screens.volunteer.qr.VolunteerQrReaderFragment

internal class FragmentsScreen {

    class SplashScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return SplashFragment.newInstance()
        }
    }

    class AgreementScreen(val url: String) : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return HtmlViewerFragment.newUrlInstance(url)
        }
    }

    class RegistrationWithScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return RegistrationWithFragment.newInstance()
        }
    }

    class RegistrationEmailScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return RegistrationEmailFragment.newInstance()
        }
    }

    class ParticipantQrScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return ParticipantQrCodeFragment.newInstance()
        }
    }

    class ParticipantAboutFestivalScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return ParticipantAboutFestivalFragment.newInstance()
        }
    }

    class ParticipantAboutMovementScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return ParticipantAboutMovementFragment.newInstance()
        }
    }

    class VolunteerLoginScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return VolunteerLoginFragment.newInstance()
        }
    }

    class VolunteerQrReaderScreen() : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return VolunteerQrReaderFragment.newInstance()
        }
    }

}