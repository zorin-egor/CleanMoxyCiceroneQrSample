package com.sample.qr.presentation.di.modules

import com.sample.qr.domain.interactors.login.email.RegistrationEmailInteractor
import com.sample.qr.domain.interactors.login.email.RegistrationEmailInteractorImpl
import com.sample.qr.domain.interactors.login.with.RegistrationWithInteractor
import com.sample.qr.domain.interactors.login.with.RegistrationWithInteractorImpl
import com.sample.qr.domain.interactors.participant.ParticipantInteractor
import com.sample.qr.domain.interactors.participant.ParticipantInteractorImpl
import com.sample.qr.domain.interactors.splash.SplashInteractor
import com.sample.qr.domain.interactors.splash.SplashInteractorImpl
import com.sample.qr.domain.interactors.volunteer.VolunteerInteractor
import com.sample.qr.domain.interactors.volunteer.VolunteerInteractorImpl
import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository
import com.sample.qr.presentation.di.PresentationScope
import dagger.Module
import dagger.Provides

@Module
internal class InteractorsModule {

    @Provides
    @PresentationScope
    fun provideSplashInteractor(repository: PreferenceRepository): SplashInteractor =
            SplashInteractorImpl(repository)

    @Provides
    @PresentationScope
    fun provideRegistrationWithInteractor(repository: PreferenceRepository): RegistrationWithInteractor =
            RegistrationWithInteractorImpl(repository)

    @Provides
    @PresentationScope
    fun provideRegistrationEmailInteractor(repository: MainRepository): RegistrationEmailInteractor =
            RegistrationEmailInteractorImpl(repository)

    @Provides
    @PresentationScope
    fun provideParticipantInteractor(repository: MainRepository): ParticipantInteractor =
            ParticipantInteractorImpl(repository)

    @Provides
    @PresentationScope
    fun provideVolunteerInteractor(repository: MainRepository): VolunteerInteractor =
            VolunteerInteractorImpl(repository)

}