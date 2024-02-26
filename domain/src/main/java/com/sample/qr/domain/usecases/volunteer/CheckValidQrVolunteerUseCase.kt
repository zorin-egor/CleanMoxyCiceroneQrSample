package com.sample.qr.domain.usecases.volunteer

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.repositories.MainRepository

class CheckValidQrVolunteerUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(value: String): Data<Boolean> {
        return repository.isValidQr(value)
    }

}