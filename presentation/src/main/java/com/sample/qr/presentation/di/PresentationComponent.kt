package com.sample.qr.presentation.di

import android.app.Application
import com.sample.qr.domain.di.DomainComponent
import com.sample.qr.presentation.di.modules.NavigationModule
import dagger.BindsInstance
import dagger.Component


@PresentationScope
@Component(
    dependencies = [
        DomainComponent::class
    ],
    modules = [
        NavigationModule::class
    ]
)
interface PresentationComponent : Injects {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun with(app: Application): Builder

        fun with(component: DomainComponent): Builder

        fun build(): PresentationComponent
    }

}