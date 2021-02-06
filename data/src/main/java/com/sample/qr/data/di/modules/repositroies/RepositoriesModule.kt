package com.sample.qr.data.di.modules.repositroies

import android.app.Application
import com.sample.qr.data.repositories.MainRepositoryImpl
import com.sample.qr.data.repositories.PreferenceRepositoryImpl
import com.sample.qr.data.services.ApiService
import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository
import dagger.Module
import dagger.Provides

@Module
internal class RepositoriesModule {

    @Provides
    fun providePreferenceRepository(app: Application): PreferenceRepository =
            PreferenceRepositoryImpl(app)

    @Provides
    fun provideMainRepository(repository: PreferenceRepository, api: ApiService): MainRepository =
            MainRepositoryImpl(api, repository)

}