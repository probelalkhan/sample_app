package net.simplifiedcoding.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User)

    @Query("SELECT * FROM user WHERE id = 0")
    fun getCurrentUser() : LiveData<User>
}