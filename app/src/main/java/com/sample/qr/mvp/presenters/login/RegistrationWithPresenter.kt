package com.sample.qr.mvp.presenters.login

import com.arellomobile.mvp.InjectViewState
import com.sample.qr.App
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.views.login.RegistrationWithView

@InjectViewState
class RegistrationWithPresenter : BasePresenter<RegistrationWithView>() {

    companion object {
        val TAG = RegistrationWithPresenter::class.java.simpleName
    }

    init {
        App.instance.getAppComponent().inject(this)
    }

    private fun setAgreementState() {
        mPreferenceTool.isAgreement().let {
            viewState.onAgreementCheckBox(it)
            viewState.onEmailButtonEnable(it)
        }
    }

    fun init() {
        setAgreementState()
    }

    fun setCheckBox(value: Boolean) {
        mPreferenceTool.setAgreement(value)
        setAgreementState()
    }

}