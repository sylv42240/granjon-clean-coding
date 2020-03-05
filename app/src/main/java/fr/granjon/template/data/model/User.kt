package fr.granjon.template.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @SerializedName("login") val login: String,
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("node_id") val node_id: String,
    @SerializedName("avatar_url") val avatar_url: String,
    @SerializedName("gravatar_id") val gravatar_id: String,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val html_url: String,
    @SerializedName("followers_url") val followers_url: String,
    @SerializedName("following_url") val following_url: String,
    @SerializedName("gists_url") val gists_url: String,
    @SerializedName("starred_url") val starred_url: String,
    @SerializedName("subscriptions_url") val subscriptions_url: String,
    @SerializedName("organizations_url") val organizations_url: String,
    @SerializedName("repos_url") val repos_url: String,
    @SerializedName("events_url") val events_url: String,
    @SerializedName("received_events_url") val received_events_url: String,
    @SerializedName("type") val type: String,
    @SerializedName("site_admin") val site_admin: Boolean,
    @SerializedName("created_at") val created_at: String?= null,
    @SerializedName("public_repos") val public_repos: Int?= null,
    @SerializedName("followers") val followers: Int?= null,
    @SerializedName("location") val location: String?= null,
    @SerializedName("email") val email: String?= null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("blog") val blog: String? = null,
    var savedInDatabase: Boolean = false
)