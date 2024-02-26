package com.sample.qr.domain.usecases.registration.email

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Success
import com.sample.qr.domain.repositories.MainRepository

internal class RegistrationByEmailUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(name: String, surname: String, mail: String, captcha: String): Data<Boolean> {
        return repository.register(name, surname, mail, captcha).also {
            if (it is Success) {
                repository.setParticipantLogin(true)
            }
        }
    }

}