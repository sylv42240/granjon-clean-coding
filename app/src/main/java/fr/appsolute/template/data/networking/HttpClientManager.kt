package fr.appsolute.template.data.networking

import fr.appsolute.template.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


private object HttpClientManagerImpl : HttpClientManager {

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG)
                    this.addInterceptor(HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    })
            }
            .build()

    override val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

interface HttpClientManager {
    val retrofit: Retrofit

    companion object Instance {
        val instance: HttpClientManager = HttpClientManagerImpl
    }
}

inline fun <reified T> HttpClientManager.createApi(): T {
    return this.retrofit.create()
}
