package com.sample.qr.domain.usecases.agreement

import com.sample.qr.domain.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetAgreementUseCase(
    private val repository: PreferenceRepository
) {

    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {
            repository.isAgreement()
        }
    }

}