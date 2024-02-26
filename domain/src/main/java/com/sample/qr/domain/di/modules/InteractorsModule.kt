package com.sample.qr.domain.di.modules

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
import com.sample.qr.domain.usecases.agreement.GetAgreementUseCase
import com.sample.qr.domain.usecases.agreement.SetAgreementUseCase
import com.sample.qr.domain.usecases.captcha.CaptchaUseCase
import com.sample.qr.domain.usecases.login.CheckAuthUseCase
import com.sample.qr.domain.usecases.participant.CheckLoginParticipantUseCase
import com.sample.qr.domain.usecases.participant.GetEventParticipantUseCase
import com.sample.qr.domain.usecases.participant.GetNameParticipantUseCase
import com.sample.qr.domain.usecases.participant.GetQrParticipantUseCase
import com.sample.qr.domain.usecases.participant.LogoutParticipantUseCase
import com.sample.qr.domain.usecases.registration.email.RegistrationByEmailUseCase
import com.sample.qr.domain.usecases.volunteer.CheckValidQrVolunteerUseCase
import com.sample.qr.domain.usecases.volunteer.LoginVolunteerUseCase
import com.sample.qr.domain.usecases.volunteer.LogoutVolunteerUseCase
import dagger.Module
import dagger.Provides

@Module
internal class InteractorsModule {

    @Provides
    fun provideSplashInteractor(
        checkAuthUseCase: CheckAuthUseCase,
        isParticipantUseCase: CheckLoginParticipantUseCase
    ): SplashInteractor = SplashInteractorImpl(checkAuthUseCase, isParticipantUseCase)

    @Provides
    fun provideRegistrationEmailInteractor(
        registrationByEmailUseCase: RegistrationByEmailUseCase,
        captchaUseCase: CaptchaUseCase
    ): RegistrationEmailInteractor = RegistrationEmailInteractorImpl(registrationByEmailUseCase, captchaUseCase)

    @Provides
    fun provideRegistrationWithInteractor(
        agreementSetUseCase: SetAgreementUseCase,
        agreementGetUseCase: GetAgreementUseCase
    ): RegistrationWithInteractor = RegistrationWithInteractorImpl(agreementSetUseCase, agreementGetUseCase)

    @Provides
    fun provideParticipantInteractor(
        logoutParticipantUseCase: LogoutParticipantUseCase,
        getEventParticipantUseCase: GetEventParticipantUseCase,
        getQrParticipantUseCase: GetQrParticipantUseCase,
        getNameParticipantUseCase: GetNameParticipantUseCase
    ): ParticipantInteractor = ParticipantInteractorImpl(logoutParticipantUseCase, getEventParticipantUseCase,
        getQrParticipantUseCase, getNameParticipantUseCase)

    @Provides
    fun provideVolunteerInteractor(
        volunteerLogoutUseCase: LogoutVolunteerUseCase,
        volunteerLoginVolunteerUseCase: LoginVolunteerUseCase,
        volunteerQrVolunteerUseCase: CheckValidQrVolunteerUseCase
    ): VolunteerInteractor = VolunteerInteractorImpl(volunteerLogoutUseCase, volunteerLoginVolunteerUseCase, volunteerQrVolunteerUseCase)

}
