package com.sample.qr.mvp.presenters.base

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpPresenter
import com.samokatnn.lamboscooter.managers.utils.Coroutine
import com.sample.qr.R
import com.sample.qr.managers.exceptions.AuthException
import com.sample.qr.managers.exceptions.ConnectionException
import com.sample.qr.managers.tools.PreferenceTool
import com.sample.qr.mvp.views.base.BaseView
import kotlinx.coroutines.Dispatchers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BasePresenter<View: BaseView> : MvpPresenter<View>() {

    companion object {
        val TAG = BasePresenter::class.java.simpleName
    }

    @Inject
    protected lateinit var mContext: Context

    @Inject
    protected lateinit var mPreferenceTool: PreferenceTool

    @Inject
    protected lateinit var mRouter: Router

    protected val mRoutinesCommon = Coroutine()
    protected val mRoutinesIO = Coroutine(Dispatchers.IO)

    protected fun getString(@StringRes value: Int): String {
        return mContext.getString(value)
    }

    protected open fun handlerError(error: Throwable) {
        when(error) {
            is AuthException -> {
                viewState.onMessage(getString(R.string.error_auth))
            }

            is ConnectionException -> {
                viewState.onMessage(getString(R.string.error_network))
            }

            else -> {
                viewState.onMessage(getString(R.string.error_unknown))
                Log.e(TAG, "handlerError()", error)
            }
        }
    }

    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    open fun onBackPressed(): Boolean {
        return false
    }

}