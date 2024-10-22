package com.sample.qr.presentation.ui.screens.base

import android.app.Application
import androidx.annotation.StringRes
import com.github.terrakok.cicerone.Router
import com.sample.qr.domain.models.Error
import com.sample.qr.presentation.R
import moxy.MvpPresenter

abstract class BasePresenter<View: BaseView>(
        protected val app: Application,
        protected val router: Router
) : MvpPresenter<View>() {

    protected fun getString(@StringRes value: Int, vararg args: String?): String {
        return app.getString(value, *args)
    }

    protected open fun handlerError(error: Error? = null) {
        when {
            error?.error != null -> viewState.onMessage(getString(R.string.error_common, error.error))
            else -> viewState.onMessage(getString(R.string.error_unknown))
        }
    }

}