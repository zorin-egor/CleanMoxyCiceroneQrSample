package com.sample.qr.presentation.ui.screens.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.di.PresentationProvider
import com.sample.qr.presentation.extensions.getSnackBar
import moxy.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity() {

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    interface OnBackPressFragment {
        fun onBackPressed(): Boolean
    }

    @Inject
    protected lateinit var mRouter: Router

    @Inject
    protected lateinit var mNavigatorHolder: NavigatorHolder

    private var mSnackBar: Snackbar? = null
    private val mNavigator: Navigator
    private val presentationComponent: PresentationComponent
        get() = (applicationContext as? PresentationProvider)?.presentationComponent
                ?: throw IllegalStateException("Application context must be implement ${PresentationProvider::class.simpleName}")

    init {
        mNavigator = SupportAppNavigator(this, getNavigationId())
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
        mSnackBar = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        Log.d(TAG, "$this-onBackPressed()")
        if (!isFragmentBackPress()) {
//            mRouter.exit()
            super.onBackPressed()
        }
    }

    protected fun isFragmentBackPress(): Boolean {
        supportFragmentManager.fragments.asReversed().forEach { parentFragment ->
            if (isFragmentBackPress(parentFragment)) {
                return true
            }
        }
        return false
    }

    protected fun isFragmentBackPress(fragment: Fragment): Boolean {
        fragment.childFragmentManager.fragments.asReversed().forEach { childFragment ->
            if (isFragmentBackPress(childFragment)) {
                return true
            }
        }
        return fragment is OnBackPressFragment && fragment.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "$this-onResume()")
        mNavigatorHolder.setNavigator(mNavigator)
    }

    override fun onPause() {
        Log.d(TAG, "$this-onPause()")
        mNavigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun init(savedInstanceState: Bundle?) {
        mSnackBar = findViewById<View>(android.R.id.content).getSnackBar(R.color.colorDarkGreyTint)
    }

    protected fun showSnackBar(@StringRes resource: Int): Snackbar {
        return showSnackBar(resources.getString(resource))
    }

    protected fun showSnackBar(string: String): Snackbar {
        return mSnackBar?.apply {
            setText(string)
            setAction(null, null)
            show()
        } ?: throw IllegalStateException("Snackbar must be set")
    }

}