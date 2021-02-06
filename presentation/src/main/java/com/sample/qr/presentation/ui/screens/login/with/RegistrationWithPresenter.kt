package com.sample.qr.presentation.ui.screens.login.with

import android.app.Application
import com.sample.qr.domain.interactors.login.with.RegistrationWithInteractor
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RegistrationWithPresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: RegistrationWithInteractor
) : BasePresenter<RegistrationWithView>(app, router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setAgreementState()
    }

    private fun setAgreementState() {
        presenterScope.launch {
            val isAgreement = interactor.getAgreement()
            viewState.onAgreementCheckBox(isAgreement)
            viewState.onEmailButtonEnable(isAgreement)
        }
    }

    fun setCheckBox(value: Boolean) {
        presenterScope.launch {
            interactor.setAgreement(value)
            setAgreementState()
        }
    }
}