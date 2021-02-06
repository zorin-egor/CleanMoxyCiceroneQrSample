package com.sample.qr.presentation.ui.screens.splash

import android.app.Application
import com.sample.qr.domain.interactors.splash.SplashInteractor
import com.sample.qr.presentation.navigation.ActivitiesScreen
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import ru.terrakok.cicerone.Router
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
                    router.newRootScreen(ActivitiesScreen.ParticipantScreen())
                }
                isAuthenticated && !isParticipantLogin -> {
                    router.newRootScreen(ActivitiesScreen.VolunteerScreen())
                }
                else -> {
                    router.newRootScreen(ActivitiesScreen.RegistrationScreen())
                }
            }
        }
    }

}