package com.sample.qr.presentation.di

import androidx.databinding.ViewDataBinding
import com.sample.qr.presentation.ui.screens.base.BaseActivity
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.screens.login.email.RegistrationEmailFragment
import com.sample.qr.presentation.ui.screens.login.with.RegistrationWithFragment
import com.sample.qr.presentation.ui.screens.participant.festival.ParticipantAboutFestivalFragment
import com.sample.qr.presentation.ui.screens.participant.movement.ParticipantAboutMovementFragment
import com.sample.qr.presentation.ui.screens.participant.qr.ParticipantQrCodeFragment
import com.sample.qr.presentation.ui.screens.splash.SplashFragment
import com.sample.qr.presentation.ui.screens.volunteer.login.VolunteerLoginFragment
import com.sample.qr.presentation.ui.screens.volunteer.qr.VolunteerQrReaderFragment


interface Injects {

    fun inject(activity: BaseActivity)

    fun inject(activity: BaseFragment<ViewDataBinding>)

    fun inject(fragment: SplashFragment)

    fun inject(fragment: RegistrationEmailFragment)

    fun inject(fragment: RegistrationWithFragment)

    fun inject(fragment: ParticipantAboutFestivalFragment)

    fun inject(fragment: ParticipantAboutMovementFragment)

    fun inject(fragment: ParticipantQrCodeFragment)

    fun inject(fragment: VolunteerLoginFragment)

    fun inject(fragment: VolunteerQrReaderFragment)

}