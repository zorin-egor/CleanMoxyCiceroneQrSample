package com.sample.qr.domain.interactors.login.email

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Success
import com.sample.qr.domain.repositories.MainRepository

class RegistrationEmailInteractorImpl(
    private val repository: MainRepository
) : RegistrationEmailInteractor {

    override suspend fun register(name: String, surname: String, mail: String, captcha: String): Data<Boolean> {
        return repository.register(name, surname, mail, captcha).also {
            if (it is Success) {
                repository.setParticipantLogin(true)
            }
        }
    }

    override suspend fun getCaptcha(): Data<String> {
        return repository.getCaptcha()
    }
}