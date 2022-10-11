package br.com.cursoideal

import android.app.Application
import br.com.cursoideal.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainAplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainAplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    firebaseModule,
                    webClientModule,
                    serviceMobule
                )
            )
        }
    }
}