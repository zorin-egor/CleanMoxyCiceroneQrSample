package com.sample.qr.domain.interactors.volunteer

import com.sample.qr.domain.models.Data
import com.sample.qr.domain.usecases.volunteer.CheckValidQrVolunteerUseCase
import com.sample.qr.domain.usecases.volunteer.LoginVolunteerUseCase
import com.sample.qr.domain.usecases.volunteer.LogoutVolunteerUseCase

internal class VolunteerInteractorImpl(
    private val volunteerLogoutUseCase: LogoutVolunteerUseCase,
    private val volunteerLoginVolunteerUseCase: LoginVolunteerUseCase,
    private val volunteerQrVolunteerUseCase: CheckValidQrVolunteerUseCase
) : VolunteerInteractor {

    override suspend fun logout() {
        volunteerLogoutUseCase()
    }

    override suspend fun login(login: String, pwd: String): Data<Boolean> {
        return volunteerLoginVolunteerUseCase(login, pwd)
    }

    override suspend fun isValidQr(value: String): Data<Boolean> {
        return volunteerQrVolunteerUseCase(value)
    }
}