package com.sample.qr.presentation.ui.screens.volunteer.login

import android.app.Application
import android.util.Patterns
import com.sample.qr.domain.interactors.volunteer.VolunteerInteractor
import com.sample.qr.domain.models.Empty
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Success
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.startNewClear
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import com.sample.qr.presentation.ui.screens.volunteer.VolunteerActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class VolunteerLoginPresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: VolunteerInteractor
) : BasePresenter<VolunteerLoginView>(app, router) {

    private var mLoginJob: Job? = null

    fun login(login: String, pwd: String) {
        if (mLoginJob?.isActive == true) {
            return
        }
        mLoginJob = presenterScope.launch {

            var isDataComplete = true

            if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
                viewState.onLoginError()
                isDataComplete = false
            }

            if (pwd.isEmpty()) {
                viewState.onPasswordError()
                isDataComplete = false
            }

            if (!isDataComplete) {
                viewState.onMessage(getString(R.string.errors_input))
                return@launch
            }

            viewState.onButtonEnabled(false)

            interactor.login(login, pwd).let {
                when (it) {
                    is Error -> handlerError(it)
                    is Empty -> handlerError()
                    is Success -> {
//                        router.newRootScreen(ActivitiesScreen.VolunteerScreen())
                        app.startNewClear<VolunteerActivity>()
                    }
                }
            }

        }.apply {
            invokeOnCompletion {
                it?.let { handlerError() }
                viewState.onButtonEnabled(true)
            }
        }
    }

}