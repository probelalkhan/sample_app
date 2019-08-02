package net.simplifiedcoding.ui.home.bargraph

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.bar_graph_fragment.*
import net.simplifiedcoding.R
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.databinding.BarGraphFragmentBinding
import net.simplifiedcoding.ui.home.detail.BarGraphViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class BarGraphFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: BarGraphViewModelFactory by instance()

    private lateinit var viewModel: BarGraphViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val binding: BarGraphFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.bar_graph_fragment, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(BarGraphViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.employees.observe(this, Observer {
            showBarChart(it)
        })

    }

    private fun showBarChart(employees: List<Employee>) {

        val entries = mutableListOf<BarEntry>()

        var i = 0
        employees.forEach {
            i++
            val s = it.salary.substring(1, it.salary.length).trim()
            val sb = StringBuilder(s).deleteCharAt(s.indexOf(','))
            val salary = sb.toString().toFloat()
            entries.add(
                BarEntry(
                    i.toFloat(),
                    salary
                )
            )
        }

        val barData = BarData(BarDataSet(entries, "Salaries"))
        chart.data = barData
    }


    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}
