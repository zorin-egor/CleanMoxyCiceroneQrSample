package com.sample.qr.data.di.modules.services

import android.app.Application
import com.sample.qr.data.services.ApiService
import com.sample.qr.data.services.ApiServiceImpl
import dagger.Module
import dagger.Provides

@Module
internal class ServicesModule {

    @Provides
    fun provideApiService(app: Application): ApiService = ApiServiceImpl(app)

}