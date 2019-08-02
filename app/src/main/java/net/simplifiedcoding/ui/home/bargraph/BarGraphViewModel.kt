package net.simplifiedcoding.ui.home.bargraph

import androidx.lifecycle.ViewModel;
import net.simplifiedcoding.data.repository.AppRepository

class BarGraphViewModel(
    private val repository: AppRepository
) : ViewModel() {

    val employees by lazy {
        repository.getTopTenEmployees()
    }
}
