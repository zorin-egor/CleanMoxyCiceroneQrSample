package com.sample.qr.di.component

import com.sample.qr.di.module.AppModule
import com.sample.qr.di.module.NavigationModule
import com.sample.qr.di.module.ToolModule
import com.sample.qr.mvp.presenters.login.RegistrationEmailPresenter
import com.sample.qr.mvp.presenters.login.RegistrationWithPresenter
import com.sample.qr.mvp.presenters.login.SplashPresenter
import com.sample.qr.mvp.presenters.participant.ParticipantAboutFestivalPresenter
import com.sample.qr.mvp.presenters.participant.ParticipantAboutMovementPresenter
import com.sample.qr.mvp.presenters.participant.ParticipantQrCodePresenter
import com.sample.qr.mvp.presenters.volunteer.VolunteerLoginPresenter
import com.sample.qr.mvp.presenters.volunteer.VolunteerQrReaderPresenter
import com.sample.qr.ui.activities.base.BaseActivity
import com.sample.qr.ui.fragments.base.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(
        AppModule::class,
        ToolModule::class,
        NavigationModule::class
))
@Singleton
interface AppComponent {

    fun inject(instance: BaseActivity)
    fun inject(instance: BaseFragment)

    fun inject(instance: SplashPresenter)
    fun inject(instance: RegistrationWithPresenter)
    fun inject(instance: RegistrationEmailPresenter)

    fun inject(instance: ParticipantQrCodePresenter)
    fun inject(instance: ParticipantAboutFestivalPresenter)
    fun inject(instance: ParticipantAboutMovementPresenter)

    fun inject(instance: VolunteerLoginPresenter)
    fun inject(instance: VolunteerQrReaderPresenter)

}