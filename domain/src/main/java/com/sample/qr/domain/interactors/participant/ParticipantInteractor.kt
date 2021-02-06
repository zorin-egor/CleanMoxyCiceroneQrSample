package com.sample.qr.domain.interactors.participant

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.models.Event

interface ParticipantInteractor {

    suspend fun logout()

    suspend fun getEvent(): Data<Event>

    suspend fun getQr(): Data<String>

    suspend fun getParticipantName(): Data<String>

}