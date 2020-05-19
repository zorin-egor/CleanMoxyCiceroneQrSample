package com.sample.qr.mvp.presenters.volunteer

import android.content.Intent
import android.net.Uri
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.vision.barcode.Barcode
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.managers.exceptions.AuthException
import com.sample.qr.managers.exceptions.ConnectionException
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.screens.ActivitiesScreen
import com.sample.qr.mvp.views.volunteer.VolunteerQrReaderView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@InjectViewState
class VolunteerQrReaderPresenter : BasePresenter<VolunteerQrReaderView>() {

    companion object {
        val TAG = VolunteerQrReaderPresenter::class.java.simpleName
    }

    private val mStateTimer: CountDownTimer = object : CountDownTimer(2000, 1000) {
        override fun onTick(time: Long) {
            // Stub
        }

        override fun onFinish() {
            mHandler.post {
                viewState.onProgressBarVisibility(false)
                viewState.onScannerState()
            }

            mIsEnabled = true
        }
    }

    private var mIsEnabled: Boolean = true
    private val mHandler = Handler(Looper.getMainLooper())
    private var mQrJob: Job? = null
    private var mRandomError: Boolean = false
    private var mState = 0

    init {
        App.instance.getAppComponent().inject(this)
    }

    fun setQrCode(code: Barcode) {
        if (mIsEnabled) {
            mIsEnabled = false

            mQrJob?.cancel()
            mQrJob = mRoutinesIO.run({ foreground, instance ->
                foreground.launch {
                    viewState.onProgressBarVisibility(true)
                }

                delay(1000)

                if (mRandomError || Random.nextBoolean()) {
                    when (mState++) {
                        0 -> {
                            foreground.launch {
                                viewState.onUnknownState()
                            }
                        }

                        1 -> {
                            foreground.launch {
                                viewState.onActivatedState()
                            }
                        }

                        else -> {
                            mState = 0

                            foreground.launch {
                                viewState.onSuccessState()
                            }

                            delay(500)

                            mContext.startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(code.rawValue)
                            }, getString(R.string.app_chooser)).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        }
                    }
                } else {
                    mRandomError = true
                    throw ConnectionException()
                }

            }, {
                mStateTimer.start()
            }, {
                handlerError(it)

                when(it) {
                    is AuthException -> {
                        mRouter.newRootScreen(ActivitiesScreen.RegistrationScreen())
                    }

                    else -> {
                        mStateTimer.start()
                    }
                }
            })
        }
    }

    fun logout() {
        mQrJob?.cancel()
        mRouter.newRootScreen(ActivitiesScreen.RegistrationScreen())
    }

}