package com.sample.qr

import android.app.Application
import com.sample.qr.presentation.di.ComponentProvider
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.di.PresentationProvider

class App : Application(), PresentationProvider {

    override val presentationComponent: PresentationComponent by lazy {
        ComponentProvider(this).presentationComponent
    }

}