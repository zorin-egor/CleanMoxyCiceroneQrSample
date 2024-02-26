package com.sample.qr.domain.di

import com.sample.qr.domain.di.modules.InteractorsModule
import com.sample.qr.domain.di.modules.UseCasesModule
import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository
import dagger.BindsInstance
import dagger.Component

@DomainScope
@Component(
    modules = [
        InteractorsModule::class,
        UseCasesModule::class
    ]
)
interface DomainComponent : DomainProviders {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun with(repository: PreferenceRepository): Builder

        @BindsInstance
        fun with(repository: MainRepository): Builder

        fun build(): DomainComponent
    }

}