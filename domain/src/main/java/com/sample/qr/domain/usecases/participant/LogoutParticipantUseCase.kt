package com.sample.qr.domain.usecases.participant

import com.sample.qr.domain.repositories.MainRepository

class LogoutParticipantUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke() {
        repository.logout()
    }

}