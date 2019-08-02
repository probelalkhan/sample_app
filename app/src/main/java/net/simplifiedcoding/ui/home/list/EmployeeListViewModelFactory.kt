package net.simplifiedcoding.ui.home.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.simplifiedcoding.data.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class EmployeeListViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeListViewModel(repository) as T
    }
}