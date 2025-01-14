package com.sample.qr.presentation.ui.screens.login.email

import android.app.Application
import android.util.Patterns
import com.github.terrakok.cicerone.Router
import com.sample.qr.domain.interactors.login.email.RegistrationEmailInteractor
import com.sample.qr.domain.models.Empty
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Success
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.toBitmap
import com.sample.qr.presentation.navigation.ActivitiesScreen
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class RegistrationEmailPresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: RegistrationEmailInteractor
) : BasePresenter<RegistrationEmailView>(app, router) {

    private var captchaJob: Job? = null
    private var registrationJob: Job? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        captcha()
    }

    fun registration(name: String, surname: String, mail: String, captcha: String) {
        if (registrationJob?.isActive == true) {
            return
        }

        registrationJob = presenterScope.launch {

            var isDataComplete = true

            if (name.isEmpty()) {
                viewState.onNameError()
                isDataComplete = false
            }

            if (surname.isEmpty()) {
                viewState.onSurnameError()
                isDataComplete = false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                viewState.onEmailError()
                isDataComplete = false
            }

            if (captcha.isEmpty()) {
                viewState.onCaptchaError()
                isDataComplete = false
            }

            if (!isDataComplete) {
                viewState.onMessage(getString(R.string.errors_input))
                return@launch
            }

            viewState.onRegistrationButtonState(false, true)

            interactor.register(name, surname, mail, captcha).let {
                when (it) {
                    is Error -> handlerError(it)
                    is Success -> {
                        router.newRootScreen(ActivitiesScreen.ParticipantScreen())
//                        app.startClearActivity<ParticipantActivity>()
                    }
                    else -> {}
                }
            }

        }.apply {
            invokeOnCompletion {
                it?.let { handlerError() }
                viewState.onRegistrationButtonState(true)
            }
        }
    }

    fun captcha() {
        if (captchaJob?.isActive == true) {
            return
        }

        captchaJob = presenterScope.launch {
            viewState.onCaptchaProgress(true)
            viewState.onCaptchaBitmap()

            interactor.getCaptcha().let {
                when (it) {
                    is Empty -> {
                        handlerError()
                        getString(R.string.text_error)
                    }
                    is Error -> {
                        handlerError(it)
                        getString(R.string.text_error)
                    }
                    is Success -> it.value
                }
            }.let {
                withContext(Dispatchers.Default) {
                    it.toBitmap(40.0f, 0xFF000000.toInt())
                }
            }.let(viewState::onCaptchaBitmap)

        }.apply {
            invokeOnCompletion {
                it?.let { handlerError() }
                viewState.onCaptchaProgress(false)
            }
        }
    }
}