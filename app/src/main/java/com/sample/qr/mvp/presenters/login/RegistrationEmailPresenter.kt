package com.sample.qr.mvp.presenters.login

import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.managers.exceptions.AuthException
import com.sample.qr.managers.extensions.toBitmap
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.screens.ActivitiesScreen
import com.sample.qr.mvp.views.login.RegistrationEmailView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random


@InjectViewState
class RegistrationEmailPresenter : BasePresenter<RegistrationEmailView>() {

    companion object {
        val TAG = RegistrationEmailPresenter::class.java.simpleName
    }

    private var mCaptchaJob: Job? = null
    private var mRegistrationJob: Job? = null
    private var mCaptcha: String = ""
    private var mRandomError: Boolean = false

    init {
        App.instance.getAppComponent().inject(this)
    }

    fun init(isFirstShow: Boolean) {
        viewState.onRegistrationButtonState(true)

        if (isFirstShow) {
            captcha()
        }
    }

    fun registration(name: String, surname: String, mail: String, captcha: String) {
        var isDataComplete = true

        if (name.isNullOrEmpty()) {
            viewState.onNameError()
            isDataComplete = false
        }

        if (surname.isNullOrEmpty()) {
            viewState.onSurnameError()
            isDataComplete = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            viewState.onEmailError()
            isDataComplete = false
        }

        if (captcha.isNullOrEmpty()) {
            viewState.onCaptchaError()
            isDataComplete = false
        }

        if (!isDataComplete) {
            viewState.onMessage(mContext.getString(R.string.errors_input))
            return
        }

        mRegistrationJob?.cancel()
        mRegistrationJob = mRoutinesIO.run({
            handlerError(it)
            viewState.onRegistrationButtonState(true)
            viewState.onMessage(mContext.getString(R.string.error_retry))
        }) { foreground ->
            foreground.launch {
                viewState.onRegistrationButtonState(false, true)
            }

            delay(1000)

            if (mCaptcha != captcha) {
                foreground.launch {
                    viewState.onCaptchaError()
                }

                throw IllegalArgumentException("Wrong captcha")
            }

            mPreferenceTool.setParticipantName(name)
            mPreferenceTool.setParticipantSurname(surname)
            mPreferenceTool.setParticipantEmail(mail)

            if (mRandomError || Random.nextBoolean()) {
                foreground.launch {
                    mRouter.navigateTo(ActivitiesScreen.ParticipantScreen())
                }
            } else {
                mRandomError = true
                throw AuthException()
            }

        }
    }

    fun captcha() {
        mCaptchaJob = mRoutinesIO.run({
            viewState.onCaptchaProgress(false)
        }, {
            viewState.onCaptchaProgress(false)
        }) { foreground ->
            foreground.launch {
                viewState.onCaptchaProgress(true)
                viewState.onCaptchaBitmap(null)
            }

            delay(1000)
            mCaptcha = UUID.randomUUID().toString().substring(0, 5)
            val bitmap = mCaptcha.toBitmap(40.0f, 0xFF000000.toInt())

            foreground.launch {
                viewState.onCaptchaBitmap(bitmap)
            }
        }
    }

}