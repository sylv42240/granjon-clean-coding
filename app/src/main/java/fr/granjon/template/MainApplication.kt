package fr.granjon.template

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import fr.granjon.template.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            if (BuildConfig.DEBUG) androidLogger(level = Level.DEBUG)
            loadKoinModules(
                listOf(
                    dataModule,
                    networkingModule,
                    repositoryModule,
                    viewModelModule,
                    otherModule,
                    adapterModule
                )
            )
        }
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}