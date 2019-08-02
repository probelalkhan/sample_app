package net.simplifiedcoding.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.db.entities.User
import net.simplifiedcoding.data.repository.AppRepository
import net.simplifiedcoding.util.Coroutines
import org.json.JSONArray
import org.json.JSONObject

class LoginViewModel(
    private val repository: AppRepository
) : ViewModel() {

    var job: Job? = null
    var user = User()

    var loginListener: LoginListener? = null

    fun getCurrentUser() = repository.getCurrentUser()

    fun onLoginButtonClick() {
        loginListener?.onStarted()

        if (user.username.isNullOrEmpty() || user.password.isNullOrEmpty()) {
            loginListener?.onFailure("Invalid email or password")
            return
        }

        job = Coroutines.main {
            try {
                val authResponse = repository.userLogin(user)
                authResponse.errorDescription?.let {
                    loginListener?.onFailure(it)
                    return@main
                }

                val tableData = JSONObject(authResponse.data)
                val employees = tableData.getJSONArray("data")

                val list = mutableListOf<Employee>()

                for (i in 0 until employees.length()) {
                    val employee = employees[i] as JSONArray
                    list.add(
                        Employee(
                            employee[3] as String,
                            employee[0] as String,
                            employee[1] as String,
                            employee[2] as String,
                            employee[4] as String,
                            employee[5] as String
                        )
                    )
                }
                repository.saveUser(user)
                repository.saveEmployees(list)
                loginListener?.onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                loginListener?.onFailure(e.message!!)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}