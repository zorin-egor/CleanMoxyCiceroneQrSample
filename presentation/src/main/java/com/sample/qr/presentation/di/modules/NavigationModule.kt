package com.sample.qr.presentation.di.modules

import com.sample.qr.presentation.di.PresentationScope
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
internal class NavigationModule {

    @Provides
    @PresentationScope
    fun provideRouterModule(): Router = Router()

    @Provides
    @PresentationScope
    fun provideNavigationHolder(router: Router): NavigatorHolder =
        Cicerone.create(router).navigatorHolder
}