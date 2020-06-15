package com.sample.qr.ui.fragments.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.managers.extensions.getSnackBar
import com.sample.qr.managers.tools.PreferenceTool
import com.sample.qr.mvp.migrate.MvpAppCompatFragment
import com.sample.qr.ui.activities.base.BaseActivity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseFragment : MvpAppCompatFragment(), BaseActivity.OnBackPressFragment {

    companion object {
        protected val TAG_TITLE = "TAG_TITLE"

        const val PERMISSION_CAMERA = 1

        const val REQUEST_CAMERA = 1000
    }

    @Inject
    protected lateinit var mRouter: Router

    @Inject
    protected lateinit var mPreferenceTool: PreferenceTool

    protected lateinit var mBaseActivity: BaseActivity
    protected var mSnackBar: Snackbar? = null
    protected var mToolbarTitle: String? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.instance.getAppComponent().inject(this)

        try {
            mBaseActivity = context as BaseActivity
        } catch (e: ClassCastException) {
            throw RuntimeException(BaseFragment::class.java.simpleName + " - must implement - " +
                    BaseActivity::class.java.simpleName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSnackBar = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mToolbarTitle != null) {
            outState.putString(TAG_TITLE, mToolbarTitle)
        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CAMERA -> {
                onCameraPermission(grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_CAMERA -> {
                onCameraResult(resultCode, requestCode, data)
            }
        }
    }

    protected open fun onReadPermission(isGranted: Boolean) {

    }

    protected open fun onCameraPermission(isGranted: Boolean) {

    }

    protected open fun onLocationPermission(isGranted: Boolean) {

    }

    protected open fun onCameraResult(resultCode: Int, requestCode: Int, data: Intent?) {

    }

    protected open fun onFilePickerResult(resultCode: Int, requestCode: Int, data: Intent?) {

    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        mSnackBar = view.getSnackBar(R.color.colorDark)
    }

    protected fun showSnackBar(string: String): Snackbar {
        return mSnackBar?.apply {
            setText(string)
            setAction(null, null)
            show()
        } ?: throw IllegalStateException("Snackbar must be set")
    }

}