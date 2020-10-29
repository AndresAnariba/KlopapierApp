package com.anariba.klopapierapp.ui.home

import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.anariba.klopapierapp.R
import com.anariba.klopapierapp.data.DmApiViewModel
import com.anariba.klopapierapp.data.model.StoreAvailabilityItem
import com.anariba.klopapierapp.list.ToiletPaperAdapter

class HomeFragment : Fragment() {

    private var dmApiViewModel: DmApiViewModel?= null

    private var storeAvailabilityItems = ArrayList<StoreAvailabilityItem>()
    private var adapter : ToiletPaperAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        dmApiViewModel = ViewModelProviders.of(this).get(DmApiViewModel()::class.java)
        val recyclerView: RecyclerView = root.findViewById(R.id.rv_tp)

        setHasOptionsMenu(true)

        dmApiViewModel!!.storesList.observe(viewLifecycleOwner, Observer { list ->

            storeAvailabilityItems.clear()
            storeAvailabilityItems = ArrayList(list)

            storeAvailabilityItems.sortByDescending { it.stock }
            storeAvailabilityItems.sortByDescending { it.isOpened }

            recyclerView.hasFixedSize()
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ToiletPaperAdapter(storeAvailabilityItems, requireContext())
            recyclerView.adapter = adapter

        })

        dmApiViewModel!!.getAllStores()
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main, menu)

        menu.findItem(R.id.action_nearest_shops).setOnMenuItemClickListener {
            findNearestShops()
            true
        }

        super.onCreateOptionsMenu(menu, inflater)

    }


    private fun findNearestShops() = runWithPermissions(Permission.ACCESS_COARSE_LOCATION){

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if(location != null){

                    for(store in storeAvailabilityItems){
                        val storeLocation = Location("store")
                        storeLocation.latitude = store.store.location.lat
                        storeLocation.longitude = store.store.location.lon

                        store.distance = location.distanceTo(storeLocation)
                    }

                    storeAvailabilityItems.sortBy { it.distance }
                    adapter!!.notifyDataSetChanged()

                } else {
                    Snackbar.make(requireView(), requireContext().resources.getString(R.string.error_loading_location), Snackbar.LENGTH_SHORT).show()
                }
            }

    }
}
