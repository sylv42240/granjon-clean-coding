package fr.appsolute.template.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.appsolute.template.data.database.dao.UserDao
import fr.appsolute.template.data.model.User

@Database(
    entities = [User::class],
    version = 2,
    exportSchema = true
)
abstract class GitHubAPIDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}