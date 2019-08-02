package net.simplifiedcoding.ui.home.map

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.map_fragment.view.*
import kotlinx.coroutines.Job
import net.simplifiedcoding.R
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.util.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.*
import net.simplifiedcoding.util.hide
import net.simplifiedcoding.util.show


class MapFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: MapViewModelFactory by instance()
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView

    private lateinit var viewModel: MapViewModel
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.map_view

        viewModel = ViewModelProviders.of(this, factory).get(MapViewModel::class.java)

        viewModel.employees.observe(this, Observer {
            findCoordinates(it)
        })

        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        try {
            MapsInitializer.initialize(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }


        mapView.getMapAsync { mMap ->
            googleMap = mMap
        }
    }

    data class EmployeeAddress(val name: String, val point: LatLng)

    private fun findCoordinates(employees: List<Employee>) {
        progressbar.show()
        val emps = mutableListOf<EmployeeAddress>()
        job = Coroutines.ioThenMain({
            employees.forEach {
                if (Geocoder.isPresent()) {
                    try {
                        val location = it.city
                        val gc = Geocoder(requireContext())
                        val addresses = gc.getFromLocationName(location, 1)
                        for (a in addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                emps.add(
                                    EmployeeAddress(
                                        it.name,
                                        LatLng(a.latitude, a.longitude)
                                    )
                                )
                            }
                        }
                    } catch (e: IOException) {
                    }
                }
            }
        }) {
            progressbar.hide()
            displayMarkers(emps)
        }

    }

    private fun displayMarkers(employees: MutableList<EmployeeAddress>) {
        if (::googleMap.isInitialized) {
            employees.forEach {
                googleMap.addMarker(
                    MarkerOptions().position(it.point)
                        .title(it.name)

                ).showInfoWindow()
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(employees[employees.size - 1].point))
        }
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        job.cancel()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}
