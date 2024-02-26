package com.sample.qr.presentation.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.setStatusBarColorRes
import com.sample.qr.presentation.extensions.setStatusBarLight
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseActivity

internal class RegistrationActivity : BaseActivity(), View.OnClickListener {

    override fun getNavigationId() = R.id.frameContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setStatusBarColorRes(R.color.colorWhite)
        setStatusBarLight(false)

        if (savedInstanceState == null) {
            router.newRootScreen(FragmentsScreen.RegistrationWithScreen())
//            supportFragmentManager.show(RegistrationWithFragment.newInstance(), R.id.frameContainer, false, null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.frameContainer)
                ?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.toolbarTextBackButton -> {
                router.exit()
//                supportFragmentManager.back()
            }
        }
    }
}
