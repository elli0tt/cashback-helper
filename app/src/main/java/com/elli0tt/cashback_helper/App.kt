package com.elli0tt.cashback_helper

import android.app.Application
import com.elli0tt.cashback_helper.di.AndroidModule
import com.elli0tt.cashback_helper.di.DatabaseModule
import com.elli0tt.cashback_helper.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                defaultModule,
                AndroidModule().module,
                DatabaseModule().module,
                NetworkModule().module
            )
        }
    }
}