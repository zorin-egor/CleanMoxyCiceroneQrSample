package com.sample.qr.domain.interactors.login.with

import com.sample.qr.domain.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationWithInteractorImpl(
    private val repository: PreferenceRepository
) : RegistrationWithInteractor {

    override suspend fun setAgreement(value: Boolean) {
        withContext(Dispatchers.IO) {
            repository.setAgreement(value)
        }
    }

    override suspend fun getAgreement(): Boolean {
        return withContext(Dispatchers.IO) {
            repository.isAgreement()
        }
    }
}