package com.sample.app.mvp.presenters.volunteer

import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.sample.app.App
import com.sample.app.R
import com.sample.app.mvp.presenters.base.BasePresenter
import com.sample.app.mvp.screens.ActivitiesScreen
import com.sample.app.mvp.views.volunteer.VolunteerLoginView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownServiceException
import kotlin.random.Random

@InjectViewState
class VolunteerLoginPresenter : BasePresenter<VolunteerLoginView>() {

    companion object {
        val TAG = VolunteerLoginPresenter::class.java.simpleName
    }

    private var mRegistrationJob: Job? = null
    private var mRandomError: Boolean = false

    init {
        App.instance.getAppComponent().inject(this)
    }

    fun login(login: String, pwd: String) {
        var isDataComplete = true

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            viewState.onLoginError()
            isDataComplete = false
        }

        if (pwd.isNullOrEmpty()) {
            viewState.onPasswordError()
            isDataComplete = false
        }

        if (!isDataComplete) {
            viewState.onMessage(mContext.getString(R.string.errors_input))
            return
        }

        mRegistrationJob?.cancel()
        mRegistrationJob = mRoutinesIO.run({ foreground, instance ->
            foreground.launch {
                viewState.onButtonEnabled(false)
            }

            delay(1000)

            if (mRandomError || Random.nextBoolean()) {
                foreground.launch {
                    mRouter.navigateTo(ActivitiesScreen.VolunteerScreen())
                }
            } else {
                mRandomError = true
                throw UnknownServiceException()
            }

        }, {
            viewState.onButtonEnabled(true)
        }, {
            handlerError(it)
            viewState.onButtonEnabled(true)
        })
    }

}