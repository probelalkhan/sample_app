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

    fun getEmployees() = db.getEmployeeDao().getEmployees()

    fun logout() = db.clearAllTables()
}