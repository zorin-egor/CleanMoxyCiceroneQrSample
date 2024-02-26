package com.sample.qr

import android.app.Application
import com.sample.qr.data.di.DataComponent
import com.sample.qr.data.di.DataComponentProvider
import com.sample.qr.data.di.DataProvider
import com.sample.qr.domain.di.DomainComponent
import com.sample.qr.domain.di.DomainComponentProvider
import com.sample.qr.domain.di.DomainProvider
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.di.PresentationComponentProvider
import com.sample.qr.presentation.di.PresentationProvider

class App : Application(), DomainProvider, DataProvider, PresentationProvider {

    override val dataComponent: DataComponent by lazy {
        DataComponentProvider(this).dataComponent
    }

    override val domainComponent: DomainComponent by lazy {
        DomainComponentProvider(
            dataComponent.providePreferenceRepository(),
            dataComponent.provideMainRepository()
        ).domainComponent
    }

    override val presentationComponent: PresentationComponent by lazy {
        PresentationComponentProvider(this, domainComponent).presentationComponent
    }


}