package fr.appsolute.template.data.networking.datasource

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.networking.api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserDataSource private constructor(
    private val api: UserApi,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, User>() {

    private val accessToken = "token 390fbf5269437609c1c8e77cc74045c049b48099"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllUsers(page = FIRST_KEY, accessToken = accessToken).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                if (params.placeholdersEnabled) callback.onResult(
                    response,
                    0,
                    MAX_USERS_COUNT,
                    null,
                    response.last().id
                ) else callback.onResult(
                    response,
                    null,
                    response.last().id
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllUsers(page = params.key , accessToken = accessToken).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                callback.onResult(
                    response,
                    response.last().id
                )
            } catch (e: Exception) {
                Log.e(TAG, "loadInitial: ", e)
            }
        }
    }

    // This method will not be used in this app
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit

    class Factory(
        private val api: UserApi,
        private val scope: CoroutineScope
    ) : DataSource.Factory<Int, User>() {
        override fun create(): DataSource<Int, User> = UserDataSource(api, scope)
    }

    companion object {
        private const val TAG: String = "UserDataSource"
        private const val FIRST_KEY = 0
        private const val MAX_USERS_COUNT = 50000
    }

}