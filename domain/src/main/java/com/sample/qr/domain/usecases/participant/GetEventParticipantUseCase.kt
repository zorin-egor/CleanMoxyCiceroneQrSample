package com.sample.qr.domain.usecases.participant

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Event
import com.sample.qr.domain.repositories.MainRepository

class GetEventParticipantUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): Data<Event> {
        return repository.getEvent()
    }

}