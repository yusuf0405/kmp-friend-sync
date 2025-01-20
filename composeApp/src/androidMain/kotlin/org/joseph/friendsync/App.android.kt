package org.joseph.friendsync

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.joseph.friendsync.core.PlatformConfiguration
import org.joseph.friendsync.di.appModules
import org.joseph.friendsync.data.di.getSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())

        INSTANCE = this
        startKoin {
            androidContext(this@AndroidApp)
            modules(appModules() + getSharedModule() + module {
                single { PlatformConfiguration(applicationContext) }
            })
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}