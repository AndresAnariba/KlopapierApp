package com.anariba.klopapierapp.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anariba.klopapierapp.R
import com.anariba.klopapierapp.data.model.StoreAvailabilityItem
import kotlinx.android.synthetic.main.item_toiletpaper.view.*


class ToiletPaperAdapter(val items : ArrayList<StoreAvailabilityItem>, val context: Context) : RecyclerView.Adapter<ToiletPaperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_toiletpaper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : StoreAvailabilityItem = items[position]

        if(item.isOpened){
            holder.status.setTextColor(context.resources.getColor(R.color.green))
            holder.status.text = context.resources.getString(R.string.opened)
        } else {
            holder.status.setTextColor(context.resources.getColor(R.color.red))
            holder.status.text = context.resources.getString(R.string.closed)
        }

        when {
            item.stock == 0 -> holder.stock.setTextColor(context.resources.getColor(R.color.red))
            item.stock <= 30 -> holder.stock.setTextColor(context.resources.getColor(R.color.orange))
            else -> holder.stock.setTextColor(context.resources.getColor(R.color.green))

        }
        holder.stock.text = item.stock.toString()

        holder.address.text = item.store.address.street + "\n" + item.store.address.zip + " " + item.store.address.city

        holder.address.setOnClickListener {
            val address = item.store.address.street + " " + item.store.address.zip + " " + item.store.address.city
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.co.in/maps?q=$address")
            )
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val status: TextView = view.tv_status
        val stock: TextView = view.tv_stock
        val address: TextView = view.tv_address
    }
}