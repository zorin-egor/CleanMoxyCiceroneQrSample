package com.sample.qr.di.module

import android.content.Context
import com.sample.qr.managers.tools.PreferenceTool
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ToolModule {

    @Provides
    @Singleton
    fun providePref(context: Context): PreferenceTool {
        return PreferenceTool(context)
    }

}
