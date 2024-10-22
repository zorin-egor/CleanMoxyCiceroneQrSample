package com.sample.qr.presentation.ui.screens.splash

import android.app.Application
import com.github.terrakok.cicerone.Router
import com.sample.qr.domain.interactors.splash.SplashInteractor
import com.sample.qr.presentation.extensions.startClearActivity
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import com.sample.qr.presentation.ui.screens.login.RegistrationActivity
import com.sample.qr.presentation.ui.screens.participant.ParticipantActivity
import com.sample.qr.presentation.ui.screens.volunteer.VolunteerActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(
    app: Application,
    router: Router,
    private val interactor: SplashInteractor
) : BasePresenter<SplashView>(app, router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        presenterScope.launch {
            delay(2000)

            val isAuthenticated = interactor.isAuthenticated()
            val isParticipantLogin = interactor.isParticipantLogin()

            when {
                isAuthenticated && isParticipantLogin -> {
                    app.startClearActivity<ParticipantActivity>()
                }
                isAuthenticated && !isParticipantLogin -> {
                    app.startClearActivity<VolunteerActivity>()
                }
                else -> {
                    app.startClearActivity<RegistrationActivity>()
                }
            }
        }
    }
}