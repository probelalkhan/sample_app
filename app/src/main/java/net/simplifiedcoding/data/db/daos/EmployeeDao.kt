package net.simplifiedcoding.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import net.simplifiedcoding.data.db.entities.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllEmployees(quotes: List<Employee>)

    @Query("SELECT * FROM Employee")
    fun getEmployees(): List<Employee>

    @Query("SELECT * FROM Employee WHERE name LIKE '%' || :query || '%' ")
    fun findEmployeeByName(query: String): List<Employee>

    @Update
    fun update(employee: Employee)

    @Query("SELECT * FROM employee WHERE id = :id")
    fun findEmployee(id: String): LiveData<Employee>

    @Query("SELECT * FROM employee LIMIT 10")
    fun getTopTenEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM Employee")
    fun getEmployeesLiveData(): LiveData<List<Employee>>


}