package com.sample.qr.domain.usecases.volunteer

import com.sample.qr.domain.repositories.MainRepository

class LogoutVolunteerUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke() {
        repository.logout()
    }

}