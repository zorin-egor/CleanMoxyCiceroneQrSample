package com.sample.qr.ui.activities.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.managers.extensions.getSnackBar
import com.sample.qr.mvp.migrate.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


abstract class BaseActivity : MvpAppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    interface OnBackPressFragment {
        fun onBackPressed(): Boolean
    }

    @Inject
    protected lateinit var mRouter: Router

    @Inject
    protected lateinit var mNavigatorHolder: NavigatorHolder
    protected open val mNavigator: Navigator

    protected var mSnackBar: Snackbar? = null

    init {
        mNavigator = SupportAppNavigator(this, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {

    }

    override fun onBackPressed() {
        if (!isFragmentBackPress()) {
            mRouter.exit()
        }
    }

    protected fun isFragmentBackPress(): Boolean {
        supportFragmentManager.fragments?.asReversed().forEach { parentFragment ->
            if (isFragmentBackPress(parentFragment)) {
                return true
            }
        }

        return false
    }

    protected fun isFragmentBackPress(fragment: Fragment): Boolean {
        fragment.childFragmentManager.fragments?.asReversed().forEach { childFragment ->
            if (isFragmentBackPress(childFragment)) {
                return true
            }
        }

        return fragment is OnBackPressFragment && fragment.onBackPressed()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        mNavigatorHolder.setNavigator(mNavigator)
    }

    override fun onPause() {
        mNavigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun init(savedInstanceState: Bundle?) {
        supportFragmentManager.addOnBackStackChangedListener(this)
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