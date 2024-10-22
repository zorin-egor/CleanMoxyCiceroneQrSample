package com.sample.qr.presentation.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.sample.qr.presentation.di.PresentationScope
import dagger.Module
import dagger.Provides

@Module
internal class NavigationModule {

    @Provides
    @PresentationScope
    fun provideRouterModule(): Router = Router()

    @Provides
    @PresentationScope
    fun provideNavigationHolder(router: Router): NavigatorHolder =
        Cicerone.create(router).getNavigatorHolder()
}