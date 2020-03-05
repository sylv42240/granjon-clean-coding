package fr.granjon.template.data.networking.api

import fr.granjon.template.BuildConfig
import fr.granjon.template.data.model.User
import fr.granjon.template.data.networking.HttpClientManager
import fr.granjon.template.data.networking.createApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class UserApiTest {

    private lateinit var instance: HttpClientManager
    private lateinit var api: UserApi

    private val mojombo = User(
        login = "mojombo",
        id = 1,
        node_id = "MDQ6VXNlcjE=",
        avatar_url = "https://avatars0.githubusercontent.com/u/1?v=4",
        gravatar_id = "",
        url = "https://api.github.com/users/mojombo",
        html_url = "https://github.com/mojombo",
        followers_url = "https://api.github.com/users/mojombo/followers",
        following_url = "https://api.github.com/users/mojombo/following{/other_user}",
        gists_url = "https://api.github.com/users/mojombo/gists{/gist_id}",
        starred_url = "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
        subscriptions_url = "https://api.github.com/users/mojombo/subscriptions",
        organizations_url = "https://api.github.com/users/mojombo/orgs",
        repos_url = "https://api.github.com/users/mojombo/repos",
        events_url = "https://api.github.com/users/mojombo/events{/privacy}",
        received_events_url = "https://api.github.com/users/mojombo/received_events",
        type = "User",
        site_admin = false,
        name = "Tom Preston-Werner",
        blog = "http://tom.preston-werner.com",
        location = "San Francisco",
        email = "tom@mojombo.com",
        public_repos = 61,
        followers = 21820,
        created_at = "2007-10-20T05:24:19Z")

    private val mojomboInUserList = User(
        login = "mojombo",
        id = 1,
        node_id = "MDQ6VXNlcjE=",
        avatar_url = "https://avatars0.githubusercontent.com/u/1?v=4",
        gravatar_id = "",
        url = "https://api.github.com/users/mojombo",
        html_url = "https://github.com/mojombo",
        followers_url = "https://api.github.com/users/mojombo/followers",
        following_url = "https://api.github.com/users/mojombo/following{/other_user}",
        gists_url = "https://api.github.com/users/mojombo/gists{/gist_id}",
        starred_url = "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
        subscriptions_url = "https://api.github.com/users/mojombo/subscriptions",
        organizations_url = "https://api.github.com/users/mojombo/orgs",
        repos_url = "https://api.github.com/users/mojombo/repos",
        events_url = "https://api.github.com/users/mojombo/events{/privacy}",
        received_events_url = "https://api.github.com/users/mojombo/received_events",
        type = "User",
        site_admin = false)



    @Before
    fun setUp() {
        instance = HttpClientManager.create()
        api = instance.createApi()
    }

    @Test
    fun getAllUsers() = runBlocking {

        // region Test initialisation
        val firstCharacter = mojomboInUserList
        // endregion

        api.getAllUsers(BuildConfig.API_TOKEN, 0, 20).apply {
            assertTrue("Request must be a success", this.isSuccessful)
            val data: List<User> =
                this.body() ?: throw IllegalStateException("Body is null")
            assertEquals(
                "First User must be mojombo", firstCharacter, data.first()
            )
            println("${this.body()?.count()}")
        }

        return@runBlocking
    }


    @Test
    fun getUserDetailsTest() = runBlocking {
        api.getUserDetails(BuildConfig.API_TOKEN, "mojombo").run {
            assertTrue("Request must be a success", this.isSuccessful)
            val data = this.body() ?: throw IllegalStateException("Body is null")
            assertEquals("User must be mojombo", mojombo, data)
        }
    }
}