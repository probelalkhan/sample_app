package net.simplifiedcoding.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.simplifiedcoding.data.db.entities.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllEmployees(quotes: List<Employee>)

    @Query("SELECT * FROM Employee")
    fun getEmployees(): List<Employee>

    @Query("SELECT * FROM Employee WHERE name LIKE '%' || :query || '%' ")
    fun findEmployeeByName(query: String): List<Employee>

}