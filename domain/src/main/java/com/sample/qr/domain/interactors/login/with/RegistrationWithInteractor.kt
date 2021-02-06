package com.sample.qr.domain.interactors.login.with

interface RegistrationWithInteractor {

    suspend fun setAgreement(value: Boolean)

    suspend fun getAgreement(): Boolean

}