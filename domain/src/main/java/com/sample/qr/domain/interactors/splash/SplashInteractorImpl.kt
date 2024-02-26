package com.sample.qr.domain.interactors.splash

import com.sample.qr.domain.usecases.login.CheckAuthUseCase
import com.sample.qr.domain.usecases.participant.CheckLoginParticipantUseCase

internal class SplashInteractorImpl(
    private val checkAuthUseCase: CheckAuthUseCase,
    private val isParticipantUseCase: CheckLoginParticipantUseCase
) : SplashInteractor {

    override suspend fun isAuthenticated(): Boolean {
        return checkAuthUseCase()
    }

    override suspend fun isParticipantLogin(): Boolean {
        return isParticipantUseCase()
    }
}