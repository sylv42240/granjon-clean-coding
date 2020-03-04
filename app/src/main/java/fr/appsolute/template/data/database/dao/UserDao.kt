package fr.appsolute.template.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.appsolute.template.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE login = :userId")
    fun selectUserById(userId: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg data: User)

}