package com.sample.qr.domain.interactors.volunteer

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Success
import com.sample.qr.domain.repositories.MainRepository

class VolunteerInteractorImpl(
    private val repository: MainRepository
) : VolunteerInteractor {

    override suspend fun logout() {
        repository.logout()
    }

    override suspend fun login(login: String, pwd: String): Data<Boolean> {
        return repository.login(login, pwd).also {
            if (it is Success) {
                repository.setParticipantLogin(false)
            }
        }
    }

    override suspend fun isValidQr(value: String): Data<Boolean> {
        return repository.isValidQr(value)
    }
}