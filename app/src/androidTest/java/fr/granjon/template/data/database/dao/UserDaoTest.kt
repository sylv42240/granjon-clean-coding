package fr.granjon.template.data.database.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import fr.granjon.template.data.database.GitHubAPIDatabase
import fr.granjon.template.data.model.User

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class UserDaoTest {

    // region userReference
    private val userReference = User(
        login = "mojombo",
        id = 1,
        node_id = "MDQ6VXNlcjE=",
        avatar_url = "https://avatars0.githubusercontent.com/u/1?v=4",
        gravatar_id = "",
        url = "https://api.github.com/users/mojombo",
        html_url = "https://github.com/mojombo",
        followers_url = "https://api.github.com/users/mojombo/followers",
        following_url = "https://api.github.com/users/mojombo/following%7B/other_user%7D",
        gists_url = "https://api.github.com/users/mojombo/gists%7B/gist_id%7D",
        starred_url = "https://api.github.com/users/mojombo/starred%7B/owner%7D%7B/repo%7D",
        subscriptions_url = "https://api.github.com/users/mojombo/subscriptions",
        organizations_url = "https://api.github.com/users/mojombo/orgs",
        repos_url = "https://api.github.com/users/mojombo/repos",
        events_url = "https://api.github.com/users/mojombo/events%7B/privacy%7D",
        received_events_url = "https://api.github.com/users/mojombo/received_events",
        type = "User",
        site_admin = false,
        name = "Tom Preston-Werner",
        blog = "http://tom.preston-werner.com/",
        location = "San Francisco",
        email = "tom@mojombo.com",
        public_repos = 61,
        followers = 21820,
        created_at = "2007-10-20T05:24:19Z",
        savedInDatabase = true
    )
    // endregion

    private lateinit var dao: UserDao

    @Before
    fun setUp() {
        dao = database.userDao
    }

    @Test
    fun insertOne() = runBlocking {
        dao.insertUser(userReference)
        assertEquals("Should be same", userReference, dao.selectUserById(userReference.login))
    }

    @Test
    fun getCount() = runBlocking {
        dao.insertUser(userReference)
        assertEquals("Should be same", 1, dao.getCount())
    }


    companion object {
        private lateinit var database: GitHubAPIDatabase

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                GitHubAPIDatabase::class.java
            ).build()
        }
    }
}