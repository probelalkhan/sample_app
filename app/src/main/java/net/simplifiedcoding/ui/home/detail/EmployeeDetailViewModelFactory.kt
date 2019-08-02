package net.simplifiedcoding.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.simplifiedcoding.data.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class EmployeeDetailViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeDetailViewModel(repository) as T
    }
}