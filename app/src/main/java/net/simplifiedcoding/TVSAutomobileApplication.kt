package net.simplifiedcoding

import android.app.Application
import net.simplifiedcoding.data.db.AppDatabase
import net.simplifiedcoding.data.network.NetworkConnectionInterceptor
import net.simplifiedcoding.data.network.TVSApi
import net.simplifiedcoding.data.preference.PreferenceProvider
import net.simplifiedcoding.data.repository.AppRepository
import net.simplifiedcoding.ui.auth.LoginViewModelFactory
import net.simplifiedcoding.ui.home.HomeViewModelFactory
import net.simplifiedcoding.ui.home.list.EmployeeListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class TVSAutomobileApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@TVSAutomobileApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { TVSApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AppRepository(instance(), instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { EmployeeListViewModelFactory(instance()) }


    }

}