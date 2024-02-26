package com.sample.qr.presentation.di

import android.app.Application
import com.sample.qr.domain.di.DomainComponent


class PresentationComponentProvider(
    app: Application,
    domainComponent: DomainComponent
) {

    val presentationComponent: PresentationComponent by lazy {
        DaggerPresentationComponent.builder()
                .with(app)
                .with(domainComponent)
                .build()
    }

}