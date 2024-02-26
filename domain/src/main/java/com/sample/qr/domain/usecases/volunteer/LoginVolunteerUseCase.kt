package com.sample.qr.domain.usecases.volunteer

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Success
import com.sample.qr.domain.repositories.MainRepository

class LoginVolunteerUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(login: String, pwd: String): Data<Boolean> {
        return repository.login(login, pwd).also {
            if (it is Success) {
                repository.setParticipantLogin(false)
            }
        }
    }

}