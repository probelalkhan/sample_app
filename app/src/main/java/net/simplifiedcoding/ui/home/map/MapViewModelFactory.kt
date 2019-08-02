package net.simplifiedcoding.ui.home.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.simplifiedcoding.data.repository.AppRepository
import net.simplifiedcoding.ui.auth.LoginViewModel

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(repository) as T
    }
}