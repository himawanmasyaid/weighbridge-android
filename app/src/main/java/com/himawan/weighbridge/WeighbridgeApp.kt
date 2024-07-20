package com.himawan.weighbridge

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.himawan.weighbridge.di.repositoryModule
import com.himawan.weighbridge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class WeighbridgeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initFirebase()
        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@WeighbridgeApp)
            modules(
                arrayListOf(
                    repositoryModule, viewModelModule
                )
            )
        }
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

}