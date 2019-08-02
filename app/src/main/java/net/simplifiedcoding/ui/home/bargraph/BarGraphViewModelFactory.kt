package net.simplifiedcoding.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.simplifiedcoding.data.repository.AppRepository
import net.simplifiedcoding.ui.home.bargraph.BarGraphViewModel

@Suppress("UNCHECKED_CAST")
class BarGraphViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BarGraphViewModel(repository) as T
    }
}