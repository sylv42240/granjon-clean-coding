package fr.appsolute.template.data.extension

import android.content.Context
import android.util.Base64
import fr.appsolute.template.R

class TokenRetriever(private val context: Context) {
    fun getToken() : String{
        val fileName = context.getString(R.string.token_file_path)
        val encodedToken = context.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
        val token = String(Base64.decode(encodedToken, 0), Charsets.UTF_8)
        return context.getString(R.string.github_api_token, token)
    }
}