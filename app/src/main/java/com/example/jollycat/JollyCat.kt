package com.example.jollycat

import android.app.Application
import com.example.jollycat.di.dataStoreModules
import com.example.jollycat.di.databaseModules
import com.example.jollycat.di.repositoryModules
import com.example.jollycat.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JollyCat : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JollyCat)
            modules(
                dataStoreModules,
                databaseModules,
                repositoryModules,
                viewModelModules
            )
        }
    }
}