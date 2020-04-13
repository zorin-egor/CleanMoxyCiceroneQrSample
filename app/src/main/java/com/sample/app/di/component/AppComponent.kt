package com.sample.app.di.component

import com.sample.app.di.module.AppModule
import com.sample.app.di.module.NavigationModule
import com.sample.app.di.module.ToolModule
import com.sample.app.mvp.presenters.login.RegistrationEmailPresenter
import com.sample.app.mvp.presenters.login.RegistrationWithPresenter
import com.sample.app.mvp.presenters.login.SplashPresenter
import com.sample.app.mvp.presenters.participant.ParticipantAboutFestivalPresenter
import com.sample.app.mvp.presenters.participant.ParticipantAboutMovementPresenter
import com.sample.app.mvp.presenters.participant.ParticipantQrCodePresenter
import com.sample.app.mvp.presenters.volunteer.VolunteerLoginPresenter
import com.sample.app.mvp.presenters.volunteer.VolunteerQrReaderPresenter
import com.sample.app.ui.activities.base.BaseActivity
import com.sample.app.ui.fragments.base.BaseFragment
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