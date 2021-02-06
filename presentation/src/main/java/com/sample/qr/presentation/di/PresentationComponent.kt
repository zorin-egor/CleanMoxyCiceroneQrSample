package com.sample.qr.presentation.di

import android.app.Application
import com.sample.qr.data.di.DataComponent
import com.sample.qr.presentation.di.modules.InteractorsModule
import com.sample.qr.presentation.di.modules.NavigationModule
import dagger.BindsInstance
import dagger.Component


@PresentationScope
@Component(
    dependencies = [
        DataComponent::class
    ],
    modules = [
        NavigationModule::class,
        InteractorsModule::class
    ]
)
interface PresentationComponent : Injects {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun with(app: Application): Builder

        fun with(component: DataComponent): Builder

        fun build(): PresentationComponent
    }

}