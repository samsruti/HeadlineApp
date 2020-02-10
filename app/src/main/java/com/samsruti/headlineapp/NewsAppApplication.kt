package com.samsruti.headlineapp

import android.app.Application
import timber.log.Timber

class NewsAppApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

//        val appComponent = AppComponent()

//        startKoin {
//            printLogger() // Koin Logger
//            androidContext(this@NewsAppApplication)
//
//            modules(appComponent.getComponentModuleList())
//        }

    }


}
