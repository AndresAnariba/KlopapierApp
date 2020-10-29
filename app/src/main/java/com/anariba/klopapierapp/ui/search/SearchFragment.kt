package com.anariba.klopapierapp.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.anariba.klopapierapp.R
import com.anariba.klopapierapp.data.DmApiViewModel
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private var dmApiViewModel: DmApiViewModel?= null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dmApiViewModel = ViewModelProviders.of(this).get(DmApiViewModel()::class.java)

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        dmApiViewModel!!.singleStore.observe(viewLifecycleOwner, Observer {item ->
            root.cv_result.visibility = View.VISIBLE

            if(item.isOpened){
                root.tv_status.setTextColor(requireContext().resources.getColor(R.color.green))
                root.tv_status.text = requireContext().resources.getString(R.string.opened)
            } else {
                root.tv_status.setTextColor(requireContext().resources.getColor(R.color.red))
                root.tv_status.text = requireContext().resources.getString(R.string.closed)
            }

            when {
                item.stock == 0 -> root.tv_stock.setTextColor(requireContext().resources.getColor(R.color.red))
                item.stock <= 30 -> root.tv_stock.setTextColor(requireContext().resources.getColor(R.color.orange))
                else -> root.tv_stock.setTextColor(requireContext().resources.getColor(R.color.green))

            }
            root.tv_stock.text = item.stock.toString()

            root.tv_address.text = item.store.address.street + "\n" + item.store.address.zip + " " + item.store.address.city

            root.tv_address.setOnClickListener {
                val address = item.store.address.street + " " + item.store.address.zip + " " + item.store.address.city
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.co.in/maps?q=$address")
                )
                requireContext().startActivity(intent)
            }
        })

        dmApiViewModel!!.responseCode.observe(viewLifecycleOwner, Observer {
            if(it == 500){
                root.cv_result.visibility = View.GONE
                Snackbar.make(requireView(), requireContext().resources.getString(R.string.error_shop), Snackbar.LENGTH_SHORT).show()
            }
        })

        setListeners(root)
        return root
    }

    private fun setListeners(root: View) {
        root.bt_search.setOnClickListener{

            if(root.et_shopId.text.isNullOrEmpty()){
                root.cv_result.visibility = View.GONE
                Snackbar.make(requireView(), requireContext().resources.getString(R.string.no_shop_id), Snackbar.LENGTH_SHORT).show()
            } else {
                dmApiViewModel?.getSingleStoreFromId(root.et_shopId.text.toString().toInt())
            }

        }
    }
}
