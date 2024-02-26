package com.sample.qr.domain.di

import com.sample.qr.domain.repositories.MainRepository
import com.sample.qr.domain.repositories.PreferenceRepository

class DomainComponentProvider(
    preferenceRepository: PreferenceRepository,
    mainRepository: MainRepository
) : DomainProvider {

    override val domainComponent: DomainComponent by lazy {
        DaggerDomainComponent.builder()
                .with(preferenceRepository)
                .with(mainRepository)
                .build()
    }

}