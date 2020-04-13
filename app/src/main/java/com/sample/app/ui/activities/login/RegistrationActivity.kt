package com.sample.app.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sample.app.R
import com.sample.app.mvp.screens.FragmentsScreen
import com.sample.app.ui.activities.base.BaseActivity
import com.sample.app.ui.binders.ToolbarTextBinder
import kotlinx.android.synthetic.main.activity_registration.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class RegistrationActivity : BaseActivity(), View.OnClickListener {

    companion object {
        val TAG = RegistrationActivity::class.java.simpleName
    }

    override var mNavigator = SupportAppNavigator(this, R.id.frameContainer)

    val toolbarBinder: ToolbarTextBinder by lazy {
        ToolbarTextBinder(registrationToolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setStatusBarColor(R.color.colorWhite)
        setStatusBarLight(false)

        toolbarBinder.apply {
            setOnBackClickListener(this@RegistrationActivity)
            setVisibility(true)
            setSeparatorVisibility(false)
        }

        if (savedInstanceState == null) {
            mRouter.newRootScreen(FragmentsScreen.RegistrationWithScreen())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.frameContainer)?.let {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.toolbarTextBackButton -> {
                mRouter.exit()
            }
        }
    }
}
