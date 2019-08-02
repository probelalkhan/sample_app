package net.simplifiedcoding.ui.home.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.repository.AppRepository
import androidx.databinding.BindingAdapter
import kotlinx.android.synthetic.main.employee_detail_fragment.*


class EmployeeDetailViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private var _currentEmployee = MutableLiveData<Employee>()
    val currentEmployee: LiveData<Employee>
        get() = _currentEmployee


    fun updateEmployee(employee: Employee) = repository.updateEmployee(employee);

    fun findEmployee(id: String): LiveData<Employee> {
        val employee = repository.findEmployee(id)
        employee.observeForever {
            _currentEmployee.postValue(it)
        }
        return employee
    }


}
