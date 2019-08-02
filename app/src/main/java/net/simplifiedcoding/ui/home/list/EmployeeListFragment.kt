package net.simplifiedcoding.ui.home.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.employee_list_fragment.*

import net.simplifiedcoding.R
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.databinding.EmployeeListFragmentBinding
import net.simplifiedcoding.ui.home.detail.EmployeeDetailFragment
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
        val binding: EmployeeListFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.employee_list_fragment, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(EmployeeListViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(EmployeeListViewModel::class.java)
        viewModel.employees.observe(this, Observer {
            it?.let {
                initRecyclerView(it.toEmployeeItem())
            }
        })
    }


    private fun initRecyclerView(employee: List<EmployeeItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(employee)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    private fun List<Employee>.toEmployeeItem(): List<EmployeeItem> {
        return this.map {
            EmployeeItem(it)
        }
    }

}
