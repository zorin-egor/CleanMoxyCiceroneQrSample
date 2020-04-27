package com.sample.qr

import android.app.Application
import android.content.Context
import com.sample.qr.di.component.AppComponent
import com.sample.qr.di.component.DaggerAppComponent
import com.sample.qr.di.module.AppModule

class App : Application() {

    companion object {
        val TAG = App::class.java.simpleName
        lateinit var instance: App
    }

    private lateinit var mAppComponent: AppComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        instance = this
        initDagger()
    }

    private fun initDagger() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

}