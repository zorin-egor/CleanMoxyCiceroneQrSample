package com.sample.qr.presentation.ui.screens.participant.qr

import android.app.Application
import com.sample.qr.domain.interactors.participant.ParticipantInteractor
import com.sample.qr.domain.models.Empty
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Success
import com.sample.qr.presentation.extensions.toQrCode
import com.sample.qr.presentation.navigation.ActivitiesScreen
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ParticipantQrCodePresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: ParticipantInteractor
) : BasePresenter<ParticipantQrCodeView>(app, router) {

    companion object {
        private const val QR_SIZE = 350
    }

    private var logoutJob: Job? = null
    private var qrJob: Job? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getName()
        getQr()
    }

    private fun getName() {
        presenterScope.launch {
            interactor.getParticipantName().let {
                if (it is Success) {
                    viewState.onParticipantName(it.value)
                }
            }
        }
    }

    fun getQr() {
        if (qrJob?.isActive == true) {
            return
        }

        qrJob = presenterScope.launch {

            viewState.onProgressVisibility(true)

            interactor.getQr().let {
                when (it) {
                    is Error -> handlerError(it)
                    is Empty -> handlerError()
                    is Success -> {
                        withContext(Dispatchers.Default) {
                            it.value.toQrCode(QR_SIZE, QR_SIZE)
                        }.also(viewState::onQrCodeReady)
                    }
                }
            }

        }.apply {
            invokeOnCompletion {
                it?.let { handlerError() }
                viewState.onProgressVisibility(false)
            }
        }
    }

    fun logout() {
        if (logoutJob?.isActive == true) {
            return
        }
        logoutJob = presenterScope.launch {
            interactor.logout()
            router.newRootScreen(ActivitiesScreen.RegistrationScreen())
//            app.startClearActivity<RegistrationActivity>()
        }
    }
}