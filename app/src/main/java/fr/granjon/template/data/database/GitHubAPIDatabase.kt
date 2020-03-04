package fr.granjon.template.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.granjon.template.data.database.dao.UserDao
import fr.granjon.template.data.model.User

@Database(
    entities = [User::class],
    version = 2,
    exportSchema = true
)
abstract class GitHubAPIDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}