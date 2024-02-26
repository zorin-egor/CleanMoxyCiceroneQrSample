package com.sample.qr.domain.di.modules

import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository
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
internal class UseCasesModule {

    @Provides
    fun provideGetAgreementUseCase(preferenceRepository: PreferenceRepository): GetAgreementUseCase =
        GetAgreementUseCase(preferenceRepository)

    @Provides
    fun provideSetAgreementUseCase(preferenceRepository: PreferenceRepository): SetAgreementUseCase =
        SetAgreementUseCase(preferenceRepository)

    @Provides
    fun provideCaptchaUseCase(mainRepository: MainRepository): CaptchaUseCase =
        CaptchaUseCase(mainRepository)

    @Provides
    fun provideCheckAuthUseCase(preferenceRepository: PreferenceRepository): CheckAuthUseCase =
        CheckAuthUseCase(preferenceRepository)

    @Provides
    fun provideCheckLoginParticipantUseCase(preferenceRepository: PreferenceRepository): CheckLoginParticipantUseCase =
        CheckLoginParticipantUseCase(preferenceRepository)

    @Provides
    fun provideGetEventParticipantUseCase(mainRepository: MainRepository): GetEventParticipantUseCase =
        GetEventParticipantUseCase(mainRepository)

    @Provides
    fun provideGetNameParticipantUseCase(mainRepository: MainRepository): GetNameParticipantUseCase =
        GetNameParticipantUseCase(mainRepository)

    @Provides
    fun provideGetQrParticipantUseCase(mainRepository: MainRepository): GetQrParticipantUseCase =
        GetQrParticipantUseCase(mainRepository)

    @Provides
    fun provideLogoutParticipantUseCase(mainRepository: MainRepository): LogoutParticipantUseCase =
        LogoutParticipantUseCase(mainRepository)

    @Provides
    fun provideRegistrationByEmailUseCase(mainRepository: MainRepository): RegistrationByEmailUseCase =
        RegistrationByEmailUseCase(mainRepository)

    @Provides
    fun provideCheckValidQrVolunteerUseCase(mainRepository: MainRepository): CheckValidQrVolunteerUseCase =
        CheckValidQrVolunteerUseCase(mainRepository)

    @Provides
    fun provideLoginVolunteerUseCase(mainRepository: MainRepository): LoginVolunteerUseCase =
        LoginVolunteerUseCase(mainRepository)

    @Provides
    fun provideLogoutVolunteerUseCase(mainRepository: MainRepository): LogoutVolunteerUseCase =
        LogoutVolunteerUseCase(mainRepository)

}
