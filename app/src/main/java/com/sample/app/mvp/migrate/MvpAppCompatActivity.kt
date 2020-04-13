package com.sample.app.mvp.migrate

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.arellomobile.mvp.MvpDelegate

open class MvpAppCompatActivity : AppCompatActivity() {

    /**
     * @return The [MvpDelegate] being used by this Activity.
     */
    private val mMvpDelegate: MvpDelegate<out MvpAppCompatActivity> by lazy {
        MvpDelegate(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mMvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()
        mMvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMvpDelegate.onSaveInstanceState(outState)
        mMvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()
        mMvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onDestroyView()

        if (isFinishing) {
            mMvpDelegate.onDestroy()
        }
    }
}
