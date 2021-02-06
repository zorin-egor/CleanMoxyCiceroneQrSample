package com.sample.qr.presentation.ui.screens.base

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.di.PresentationProvider
import com.sample.qr.presentation.extensions.getSnackBar
import com.sample.qr.presentation.extensions.isResultsGranted
import moxy.MvpAppCompatFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseFragment : MvpAppCompatFragment(), BaseActivity.OnBackPressFragment,
    BaseView {

    companion object {
        const val PERMISSION_CAMERA = 1
        const val REQUEST_CAMERA = 1000
    }

    @Inject
    protected lateinit var mRouter: Router

    private var mSnackBar: Snackbar? = null

    private val presentationComponent: PresentationComponent
        get() = (requireContext().applicationContext as? PresentationProvider)?.presentationComponent
                ?: throw IllegalStateException("Application context must be implement ${PresentationProvider::class.simpleName}")

    protected open fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        provideComponent(presentationComponent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    override fun onDestroy() {
        mSnackBar = null
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CAMERA -> {
                onCameraPermission(isResultsGranted(permissions, grantResults))
            }
        }
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
    }

    protected open fun onCameraPermission(isGranted: Boolean) {
        // Stub
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