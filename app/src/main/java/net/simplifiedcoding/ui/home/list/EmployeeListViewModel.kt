package net.simplifiedcoding.ui.home.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.repository.AppRepository
import net.simplifiedcoding.util.Coroutines

class EmployeeListViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _employees = MutableLiveData<List<Employee>>()

    init {
        Coroutines.io {
            _employees.postValue(
                repository.getEmployees()
            )
        }
    }

    val employees: LiveData<List<Employee>>
        get() = _employees


    fun onSearchTextChange(query: CharSequence) {
        Log.d("SQuery", query.toString())
        Coroutines.io {
            if (query.isEmpty()) {
                _employees.postValue(
                    repository.getEmployees()
                )
                return@io
            }

            _employees.postValue(
                repository.findEmployeeByName(query.toString())
            )
        }
    }
}
