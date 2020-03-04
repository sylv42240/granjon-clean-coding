package fr.appsolute.template.data.database

import androidx.room.Room
import fr.appsolute.template.MainApplication


private class DatabaseManagerImpl(
    override val database: GitHubAPIDatabase
) : DatabaseManager

interface DatabaseManager {

    val database: GitHubAPIDatabase

    companion object {
        private const val DATABASE_NAME = "git_hub_api.db"

        @Volatile
        private var databaseManager: DatabaseManager? = null

        var override: DatabaseManager? = null

        fun getInstance(app: MainApplication? = null): DatabaseManager {
            return override ?: databaseManager ?: synchronized(this) {
                DatabaseManagerImpl(
                    Room.databaseBuilder(
                        app ?: throw IllegalStateException("No Application"),
                        GitHubAPIDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                ).also {
                    databaseManager = it
                }
            }
        }

    }
}