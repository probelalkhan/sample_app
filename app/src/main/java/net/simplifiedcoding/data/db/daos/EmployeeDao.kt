package net.simplifiedcoding.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import net.simplifiedcoding.data.db.entities.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllEmployees(quotes : List<Employee>)

    @Query("SELECT * FROM Employee")
    fun getEmployees() : LiveData<List<Employee>>


}