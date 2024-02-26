package com.sample.qr.domain.interactors.login.with

import com.sample.qr.domain.usecases.agreement.GetAgreementUseCase
import com.sample.qr.domain.usecases.agreement.SetAgreementUseCase

internal class RegistrationWithInteractorImpl(
    private val agreementSetUseCase: SetAgreementUseCase,
    private val agreementGetUseCase: GetAgreementUseCase
) : RegistrationWithInteractor {

    override suspend fun setAgreement(value: Boolean) {
        agreementSetUseCase(value)
    }

    override suspend fun getAgreement(): Boolean {
        return agreementGetUseCase()
    }
}