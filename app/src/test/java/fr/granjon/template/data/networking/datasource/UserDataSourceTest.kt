package fr.granjon.template.data.networking.datasource

import androidx.paging.PageKeyedDataSource
import androidx.paging.PageKeyedDataSource.LoadInitialParams
import fr.granjon.template.BuildConfig
import fr.granjon.template.data.model.User
import fr.granjon.template.data.networking.HttpClientManager
import fr.granjon.template.data.networking.api.UserApi
import fr.granjon.template.data.networking.createApi
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class UserDataSourceTest {

    private val testDispatcher = newSingleThreadContext("UI context")

    private lateinit var api: UserApi
    private lateinit var dataSource: UserDataSource

    private val loadInitialCallback =
        object : PageKeyedDataSource.LoadInitialCallback<Int, User>() {
            override fun onResult(
                data: MutableList<User>,
                position: Int,
                totalCount: Int,
                previousPageKey: Int?,
                nextPageKey: Int?
            ) {
                runBlocking(Dispatchers.Main) {
                    assertEquals("data should have 20 element", 20, data.count())
                    assertEquals("position should be 0", 0, position)
                    assertNull("No previous page", previousPageKey)
                }
            }

            override fun onResult(
                data: MutableList<User>,
                previousPageKey: Int?,
                nextPageKey: Int?
            ) {
                runBlocking(Dispatchers.Main) {
                    assertEquals("data must have 20 elements", 20, data.count())
                    assertNull("No previous page", previousPageKey)
                    assertEquals("Next key should be 30", 30, nextPageKey)

                }
            }
        }

    private val loadCallback = object : PageKeyedDataSource.LoadCallback<Int, User>() {
        override fun onResult(data: MutableList<User>, adjacentPageKey: Int?) {
            runBlocking(Dispatchers.Main) {
                assertEquals("data should have 20 element", 20, data.count())
                assertEquals("Next key should be 32", 32, adjacentPageKey)

            }
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = HttpClientManager.create().createApi()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.close()
    }

    @Test
    fun `load initial with place holder`() = runBlocking(Dispatchers.IO) {
        dataSource = createDataSource()
        dataSource.loadInitial(
            params = LoadInitialParams(20, true),
            callback = loadInitialCallback
        )
    }

    @Test
    fun `load initial without place holder`() = runBlocking(Dispatchers.IO) {
        dataSource = createDataSource()
        dataSource.loadInitial(
            params = LoadInitialParams(20, false),
            callback = loadInitialCallback
        )
    }


    @Test
    fun loadAfter() = runBlocking(Dispatchers.IO) {
        dataSource = createDataSource()
        dataSource.loadAfter(
            params = PageKeyedDataSource.LoadParams(2, 20),
            callback = loadCallback
        )
    }

    private fun CoroutineScope.createDataSource() =
        UserDataSource.Factory(api, this, BuildConfig.API_TOKEN).create() as UserDataSource
}