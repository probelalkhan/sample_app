package net.simplifiedcoding.ui.home.list

import androidx.lifecycle.ViewModel
import net.simplifiedcoding.data.repository.AppRepository

class EmployeeListViewModel(
    private val repository: AppRepository
) : ViewModel() {

    val employees = repository.getEmployees()
}
