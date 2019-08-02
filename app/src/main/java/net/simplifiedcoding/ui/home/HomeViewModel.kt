package net.simplifiedcoding.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.db.entities.User
import net.simplifiedcoding.data.repository.AppRepository
import net.simplifiedcoding.ui.auth.LoginListener
import net.simplifiedcoding.util.Coroutines
import org.json.JSONArray
import org.json.JSONObject

class HomeViewModel(
    private val repository: AppRepository
) : ViewModel() {

    fun logout() = repository.logout()

}