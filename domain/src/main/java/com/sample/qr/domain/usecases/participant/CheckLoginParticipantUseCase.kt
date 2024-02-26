package com.sample.qr.domain.usecases.participant

import com.sample.qr.domain.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckLoginParticipantUseCase(
    private val repository: PreferenceRepository
) {

    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {
            repository.isParticipantLogin()
        }
    }

}