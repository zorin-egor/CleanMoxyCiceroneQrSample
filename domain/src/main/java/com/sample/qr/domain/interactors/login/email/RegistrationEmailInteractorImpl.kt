package com.sample.qr.domain.interactors.login.email

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.usecases.captcha.CaptchaUseCase
import com.sample.qr.domain.usecases.registration.email.RegistrationByEmailUseCase

internal class RegistrationEmailInteractorImpl(
    private val registrationByEmailUseCase: RegistrationByEmailUseCase,
    private val captchaUseCase: CaptchaUseCase
) : RegistrationEmailInteractor {

    override suspend fun register(name: String, surname: String, mail: String, captcha: String): Data<Boolean> {
        return registrationByEmailUseCase(name, surname, mail, captcha)
    }

    override suspend fun getCaptcha(): Data<String> {
        return captchaUseCase()
    }
}