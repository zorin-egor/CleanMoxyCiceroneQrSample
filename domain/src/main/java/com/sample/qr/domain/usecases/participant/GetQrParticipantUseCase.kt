package com.sample.qr.domain.usecases.participant

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.repositories.MainRepository

class GetQrParticipantUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(): Data<String> {
        return repository.getQr()
    }

}