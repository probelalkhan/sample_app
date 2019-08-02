package net.simplifiedcoding.ui.home.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.simplifiedcoding.R

class EmployeeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = EmployeeDetailFragment()
    }

    private lateinit var viewModel: EmployeeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.employee_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EmployeeDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
