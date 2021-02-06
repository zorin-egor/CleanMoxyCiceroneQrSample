package com.sample.qr.domain.interactors.splash

import com.sample.qr.domain.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashInteractorImpl(
    private val repository: PreferenceRepository
) : SplashInteractor {

    override suspend fun isAuthenticated(): Boolean {
        return withContext(Dispatchers.IO) {
            repository.getToken() != null
        }
    }

    override suspend fun isParticipantLogin(): Boolean {
        return withContext(Dispatchers.IO) {
            repository.isParticipantLogin()
        }
    }
}