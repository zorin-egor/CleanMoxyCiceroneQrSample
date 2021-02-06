package com.sample.qr.presentation.di

import android.app.Application
import com.sample.qr.data.di.DaggerDataComponent
import com.sample.qr.data.di.DataComponent


class ComponentProvider(app: Application) {

    private val dataComponent: DataComponent by lazy {
        DaggerDataComponent.builder()
                .with(app)
                .build()
    }

    val presentationComponent: PresentationComponent by lazy {
        DaggerPresentationComponent.builder()
                .with(app)
                .with(dataComponent)
                .build()
    }

}