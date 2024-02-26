package com.sample.qr.data.di

import android.app.Application

class DataComponentProvider(
    app: Application
) : DataProvider {

    override val dataComponent: DataComponent by lazy {
        DaggerDataComponent.builder()
                .with(app)
                .build()
    }

}