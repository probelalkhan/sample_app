package net.simplifiedcoding.ui.home.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import net.simplifiedcoding.R
import net.simplifiedcoding.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class EmployeeListFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: EmployeeListViewModelFactory by instance()

    private lateinit var viewModel: EmployeeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.employee_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(EmployeeListViewModel::class.java)
        viewModel.employees.observe(this, Observer {

        })
    }
}
