package fr.appsolute.template.di

import fr.appsolute.template.data.extension.TokenRetriever
import fr.appsolute.template.data.networking.HttpClientManager
import fr.appsolute.template.data.networking.api.UserApi
import fr.appsolute.template.data.networking.createApi
import fr.appsolute.template.data.repository.UserRepository
import fr.appsolute.template.ui.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataModule = module {
//    single {
//        DatabaseManager.getInstance(androidContext().applicationContext as RickAndMortyApplication)
//    }
//    single {
//        get<DatabaseManager>().database.episodeDao
//    }
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
        UserRepository.newInstance(get())
    }
}

val otherModule = module {
    single {
        TokenRetriever(get())
    }
}

val viewModelModule = module {
    viewModel {
        UserViewModel(get(), get())
    }
}

