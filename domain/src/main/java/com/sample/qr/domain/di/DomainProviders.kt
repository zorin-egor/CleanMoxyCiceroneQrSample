package com.sample.qr.domain.di

import com.sample.qr.domain.interactors.login.email.RegistrationEmailInteractor
import com.sample.qr.domain.interactors.login.with.RegistrationWithInteractor
import com.sample.qr.domain.interactors.participant.ParticipantInteractor
import com.sample.qr.domain.interactors.splash.SplashInteractor
import com.sample.qr.domain.interactors.volunteer.VolunteerInteractor

interface DomainProviders {

    fun provideRegistrationEmailInteractor(): RegistrationEmailInteractor

    fun provideRegistrationWithInteractor(): RegistrationWithInteractor

    fun provideSplashInteractor(): SplashInteractor

    fun provideParticipantInteractor(): ParticipantInteractor

    fun provideVolunteerInteractor(): VolunteerInteractor
}