package com.sample.qr.presentation.ui.screens.volunteer.qr

import android.app.Application
import com.github.terrakok.cicerone.Router
import com.google.android.gms.vision.barcode.Barcode
import com.sample.qr.domain.interactors.volunteer.VolunteerInteractor
import com.sample.qr.domain.models.Empty
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Success
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.openUrl
import com.sample.qr.presentation.navigation.ActivitiesScreen
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class VolunteerQrReaderPresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: VolunteerInteractor
): BasePresenter<VolunteerQrReaderView>(app, router) {

    private var resetJob: Job? = null
    private var logoutJob: Job? = null
    private var qrJob: Job? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onScannerState()
    }

    private fun resetTimer() {
        resetJob?.cancel()
        resetJob = presenterScope.launch {
            delay(2000)
            viewState.onScannerState()
            viewState.onProgressBarVisibility(false)
        }
    }

    fun setQrCode(code: Barcode) {
        qrJob?.cancel()
        qrJob = presenterScope.launch {

            viewState.onProgressBarVisibility(true)

            interactor.isValidQr(code.rawValue).let {
                when (it) {
                    is Error -> {
                        handlerError(it)
                        viewState.onUnknownState()
                    }
                    is Empty -> {
                        handlerError()
                        viewState.onUnknownState()
                    }
                    is Success -> {
                        if (it.value) {
                            viewState.onSuccessState()
                            app.openUrl(code.rawValue, getString(R.string.app_chooser))
                        } else {
                            viewState.onActivatedState()
                        }
                    }
                }
            }
        }.apply {
            invokeOnCompletion {
                it?.let { handlerError() }
                resetTimer()
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