package fr.granjon.template.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.granjon.template.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE login = :userId")
    fun selectUserById(userId: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg data: User)

    @Query("SELECT COUNT(*) from user")
    fun getCount(): Int

}