package net.simplifiedcoding.data.repository

import net.simplifiedcoding.data.db.AppDatabase
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.db.entities.User
import net.simplifiedcoding.data.network.TVSApi


class AppRepository(
    private val api: TVSApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(user: User) = apiRequest { api.userLogin(user) }

    suspend fun saveEmployees(employees: List<Employee>) = db.getEmployeeDao().saveAllEmployees(employees)

    fun getCurrentUser() = db.getUserDao().getCurrentUser()

    fun getEmployees() = db.getEmployeeDao().getEmployees()

    fun findEmployeeByName(query: String) = db.getEmployeeDao().findEmployeeByName(query)

    fun logout() = db.getUserDao().deleteUser()

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun updateEmployee(employee: Employee) = db.getEmployeeDao().update(employee)

    fun findEmployee(id: String) = db.getEmployeeDao().findEmployee(id)

    fun getTopTenEmployees() = db.getEmployeeDao().getTopTenEmployees()

    fun getEmployeeLiveData() = db.getEmployeeDao().getEmployeesLiveData()

    fun deleteUser() = db.getUserDao().deleteUser()
}