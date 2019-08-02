package net.simplifiedcoding.ui.home.map

import androidx.lifecycle.ViewModel;
import net.simplifiedcoding.data.repository.AppRepository

class MapViewModel(
    private val repository: AppRepository
) : ViewModel() {

    val employees = repository.getEmployeeLiveData()
}
