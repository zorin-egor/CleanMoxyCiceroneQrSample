package com.sample.qr.domain.interactors.participant

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Event
import com.sample.qr.domain.repositories.MainRepository

class ParticipantInteractorImpl(
    private val repository: MainRepository
) : ParticipantInteractor {

    override suspend fun logout() {
        repository.logout()
    }

    override suspend fun getEvent(): Data<Event> {
        return repository.getEvent()
    }

    override suspend fun getQr(): Data<String> {
        return repository.getQr()
    }

    override suspend fun getParticipantName(): Data<String> {
        return repository.getParticipantName()
    }
}