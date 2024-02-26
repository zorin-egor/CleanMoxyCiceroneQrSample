package com.sample.qr.data.di

import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository

interface RepositoriesProvider {

    fun providePreferenceRepository(): PreferenceRepository

    fun provideMainRepository(): MainRepository

}