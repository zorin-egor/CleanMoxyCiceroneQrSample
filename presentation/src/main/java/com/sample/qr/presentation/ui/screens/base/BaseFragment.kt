package com.sample.qr.presentation.ui.screens.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.di.PresentationProvider
import com.sample.qr.presentation.extensions.getSnackBar
import moxy.MvpAppCompatFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding> : MvpAppCompatFragment(), BaseActivity.OnBackPressFragment,
    BaseView {

    companion object {
        private val TAG = BaseFragment::class.java.simpleName
        private const val UNDEFINED_VALUE = -1
    }

    private val tag = javaClass.simpleName
    
    @Inject
    protected lateinit var router: Router

    protected open val layoutId: Int = UNDEFINED_VALUE

    protected var viewBind: T? = null

    private var snackBar: Snackbar? = null

    private val presentationComponent: PresentationComponent
        get() = (requireContext().applicationContext as? PresentationProvider)?.presentationComponent
                ?: throw IllegalStateException("Application context must be implement ${PresentationProvider::class.simpleName}")

    protected open fun provideComponent(component: PresentationComponent) {
        component.inject(this as BaseFragment<ViewDataBinding>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(tag, "$this-onAttach()")
        provideComponent(presentationComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "$this-onCreate($savedInstanceState)")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(tag, "$this-onCreateView($savedInstanceState)")
        return if (layoutId != UNDEFINED_VALUE) {
            DataBindingUtil.inflate<T>(
                inflater,
                layoutId,
                container,
                false
            ).also { bind ->
                bind.lifecycleOwner = this
                viewBind = bind
            }.root
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "$this-onViewCreated($savedInstanceState)")
        init(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "$this-onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "$this-onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "$this-onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "$this-onStop()")
    }

    override fun onDestroyView() {
        viewBind = null
        snackBar?.dismiss()
        snackBar = null
        super.onDestroyView()
        Log.d(tag, "$this-onDestroyView()")
    }

    override fun onDestroy() {
        Log.d(tag, "$this-onDestroy(${requireActivity().isFinishing})")
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        Log.d(tag, "$this-onBackPressed()")
        return false
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        snackBar = view.getSnackBar(R.color.colorDark)
    }

    protected fun showSnackBar(
        text: String,
        actionText: String? = null,
        actionListener: View.OnClickListener? = null,
        actionColor: Int = android.R.color.white
    ): Snackbar {
        return snackBar?.apply {
            setText(text)
            setActionTextColor(ContextCompat.getColor(requireContext(), actionColor))
            setAction(actionText, actionListener)
            show()
        } ?: throw IllegalStateException("Snackbar must be set")
    }

}