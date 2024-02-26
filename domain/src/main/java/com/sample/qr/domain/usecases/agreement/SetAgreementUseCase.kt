package com.sample.qr.domain.usecases.agreement

import com.sample.qr.domain.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SetAgreementUseCase(
    private val repository: PreferenceRepository
) {

    suspend operator fun invoke(value: Boolean) {
        withContext(Dispatchers.IO) {
            repository.setAgreement(value)
        }
    }

}