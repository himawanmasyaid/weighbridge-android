package com.himawan.weighbridge

import android.app.Application
import com.himawan.weighbridge.di.appModule
import com.himawan.weighbridge.di.repositoryModule
import com.himawan.weighbridge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeighbridgeApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@WeighbridgeApp)
            modules(
                arrayListOf(
                    appModule, repositoryModule, viewModelModule
                )
            )
        }
    }


}