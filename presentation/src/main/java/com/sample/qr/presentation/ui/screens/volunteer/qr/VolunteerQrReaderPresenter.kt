package com.sample.qr.presentation.ui.screens.volunteer.qr

import android.app.Application
import com.google.android.gms.vision.barcode.Barcode
import com.sample.qr.domain.interactors.volunteer.VolunteerInteractor
import com.sample.qr.domain.models.Empty
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Success
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.openUrl
import com.sample.qr.presentation.extensions.startClearActivity
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import com.sample.qr.presentation.ui.screens.login.RegistrationActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class VolunteerQrReaderPresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: VolunteerInteractor
): BasePresenter<VolunteerQrReaderView>(app, router) {

    private var mResetJob: Job? = null
    private var mLogoutJob: Job? = null
    private var mQrJob: Job? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onScannerState()
    }

    private fun resetTimer() {
        mResetJob?.cancel()
        mResetJob = presenterScope.launch {
            delay(2000)
            viewState.onScannerState()
            viewState.onProgressBarVisibility(false)
        }
    }

    fun setQrCode(code: Barcode) {
        mQrJob?.cancel()
        mQrJob = presenterScope.launch {

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
        if (mLogoutJob?.isActive == true) {
            return
        }
        mLogoutJob = presenterScope.launch {
            interactor.logout()
//            router.newRootScreen(ActivitiesScreen.RegistrationScreen())
            app.startClearActivity<RegistrationActivity>()
        }
    }

}