package net.simplifiedcoding.ui.home.list

import androidx.navigation.Navigation
import com.xwray.groupie.databinding.BindableItem
import net.simplifiedcoding.R
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.databinding.LayoutEmployeeBinding

class EmployeeItem(
    private val employee: Employee
) : BindableItem<LayoutEmployeeBinding>() {

    override fun getLayout() = R.layout.layout_employee

    override fun bind(viewBinding: LayoutEmployeeBinding, position: Int) {
        viewBinding.employee = employee
        viewBinding.root.setOnClickListener {
            val action = EmployeeListFragmentDirections.actionEmployeeDetail(employee)
            Navigation.findNavController(it).navigate(action)
        }
    }
}