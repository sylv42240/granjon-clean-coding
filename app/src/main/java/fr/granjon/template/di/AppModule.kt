package fr.granjon.template.di

import fr.granjon.template.MainApplication
import fr.granjon.template.data.database.DatabaseManager
import fr.granjon.template.data.networking.HttpClientManager
import fr.granjon.template.data.networking.api.UserApi
import fr.granjon.template.data.networking.createApi
import fr.granjon.template.data.repository.UserRepository
import fr.granjon.template.ui.adapter.UserAdapter
import fr.granjon.template.ui.utils.dialog.DialogComponent
import fr.granjon.template.ui.utils.dialog.DialogComponentImpl
import fr.granjon.template.ui.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataModule = module {
    single {
        DatabaseManager.getInstance(androidContext().applicationContext as MainApplication)
    }
    single {
        get<DatabaseManager>().database.userDao
    }
}

val networkingModule = module {
    single {
        HttpClientManager.create()
    }
    single<UserApi> {
        get<HttpClientManager>().createApi()
    }
}

val repositoryModule = module {
    single {
        UserRepository.newInstance(get(), get())
    }
}

val otherModule = module {
    single<DialogComponent> {
        DialogComponentImpl()
    }
}

val adapterModule = module {
    factory {
        UserAdapter()
    }
}

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }
}

