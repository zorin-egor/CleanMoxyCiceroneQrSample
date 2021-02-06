package com.sample.qr.data.di

import android.app.Application
import com.sample.qr.data.di.modules.repositroies.RepositoriesModule
import com.sample.qr.data.di.modules.repositroies.RepositoriesProvider
import com.sample.qr.data.di.modules.services.ServicesModule
import dagger.BindsInstance
import dagger.Component

@DataScope
@Component(
    modules = [
        RepositoriesModule::class,
        ServicesModule::class
    ]
)
interface DataComponent : RepositoriesProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun with(application: Application): Builder

        fun build(): DataComponent
    }

}