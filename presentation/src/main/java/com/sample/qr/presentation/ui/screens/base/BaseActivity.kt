package com.sample.qr.presentation.ui.screens.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.snackbar.Snackbar
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.di.PresentationProvider
import com.sample.qr.presentation.extensions.getSnackBar
import moxy.MvpAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity() {

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    interface OnBackPressFragment {
        fun onBackPressed(): Boolean
    }

    @Inject
    protected lateinit var router: Router

    @Inject
    protected lateinit var navigatorHolder: NavigatorHolder

    private var snackBar: Snackbar? = null
    private val navigator: Navigator

    private val presentationComponent: PresentationComponent
        get() = (applicationContext as? PresentationProvider)?.presentationComponent
                ?: throw IllegalStateException("Application context must be implement ${PresentationProvider::class.simpleName}")

    init {
        navigator = AppNavigator(this, getNavigationId())
    }

    protected open fun getNavigationId(): Int = -1

    protected open fun provideComponent(component: PresentationComponent) {
        presentationComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        provideComponent(presentationComponent)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$this-task($taskId)")
        Log.d(TAG, "$this-onCreate($savedInstanceState)")
        init(savedInstanceState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "$this-onRestoreInstanceState($savedInstanceState)")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "$this-onSaveInstanceState()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "$this-onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "$this-onStart()")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "$this-onNewIntent($intent)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "$this-onStop()")
    }

    override fun onDestroy() {
        Log.d(TAG, "$this-onDestroy($isFinishing)")
        snackBar = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "$this-onResume()")
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        Log.d(TAG, "$this-onPause()")
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun init(savedInstanceState: Bundle?) {
        snackBar = findViewById<View>(android.R.id.content).getSnackBar(R.color.colorDarkGreyTint)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                router.exit()
            }
        })
    }

    protected fun showSnackBar(@StringRes resource: Int): Snackbar {
        return showSnackBar(resources.getString(resource))
    }

    protected fun showSnackBar(string: String): Snackbar {
        return snackBar?.apply {
            setText(string)
            setAction(null, null)
            show()
        } ?: throw IllegalStateException("Snackbar must be set")
    }

}