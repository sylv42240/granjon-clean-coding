package fr.appsolute.template.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.networking.HttpClientManager
import fr.appsolute.template.data.networking.api.UserApi
import fr.appsolute.template.data.networking.createApi
import fr.appsolute.template.data.networking.datasource.SearchUserDataSource
import fr.appsolute.template.data.networking.datasource.UserDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    private val paginationConfig = PagedList.Config
        .Builder()
        .setEnablePlaceholders(false)
        .setPageSize(30)
        .build()

    override fun getPaginatedList(scope: CoroutineScope, accessToken: String): LiveData<PagedList<User>> {
        return LivePagedListBuilder(
            UserDataSource.Factory(api, scope, accessToken),
            paginationConfig
        ).build()
    }

    override fun getSearchPaginatedList(scope: CoroutineScope, searchQuery: String, accessToken: String): LiveData<PagedList<User>> {
        return LivePagedListBuilder(
            SearchUserDataSource.Factory(api, scope, searchQuery, accessToken),
            paginationConfig
        ).build()
    }

    override suspend fun getCharacterDetails(id: String, accessToken: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getCharacterDetails(accessToken, id)
                check(response.isSuccessful) { "Response is not a success : code = ${response.code()}" }
                response.body() ?: throw IllegalStateException("Body is null")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}


interface UserRepository {


    fun getPaginatedList(scope: CoroutineScope, accessToken: String): LiveData<PagedList<User>>

    fun getSearchPaginatedList(scope: CoroutineScope, searchQuery: String, accessToken: String): LiveData<PagedList<User>>

    suspend fun getCharacterDetails(id: String, accessToken: String): User?

    companion object {
        val instance: UserRepository by lazy {
            UserRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }


}