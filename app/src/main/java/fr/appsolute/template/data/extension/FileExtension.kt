package fr.appsolute.template.data.extension

import android.content.Context
import android.util.Base64
import fr.appsolute.template.R

fun getToken(context: Context) : String{
    val fileName = "token.txt"
    val encodedToken = context.assets.open(fileName).bufferedReader().use{
        it.readText()
    }
    val token = String(Base64.decode(encodedToken, 0), Charsets.UTF_8)
    return context.getString(R.string.github_api_token, token)
}
