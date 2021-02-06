package com.sample.qr.domain.interactors.splash

interface SplashInteractor {

    suspend fun isAuthenticated(): Boolean

    suspend fun isParticipantLogin(): Boolean

}