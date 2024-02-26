package com.sample.qr.domain.interactors.participant

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Event
import com.sample.qr.domain.usecases.participant.GetEventParticipantUseCase
import com.sample.qr.domain.usecases.participant.GetNameParticipantUseCase
import com.sample.qr.domain.usecases.participant.GetQrParticipantUseCase
import com.sample.qr.domain.usecases.participant.LogoutParticipantUseCase

internal class ParticipantInteractorImpl(
    private val logoutParticipantUseCase: LogoutParticipantUseCase,
    private val getEventParticipantUseCase: GetEventParticipantUseCase,
    private val getQrParticipantUseCase: GetQrParticipantUseCase,
    private val getNameParticipantUseCase: GetNameParticipantUseCase
) : ParticipantInteractor {

    override suspend fun logout() {
        logoutParticipantUseCase()
    }

    override suspend fun getEvent(): Data<Event> {
        return getEventParticipantUseCase()
    }

    override suspend fun getQr(): Data<String> {
        return getQrParticipantUseCase()
    }

    override suspend fun getParticipantName(): Data<String> {
        return getNameParticipantUseCase()
    }
}