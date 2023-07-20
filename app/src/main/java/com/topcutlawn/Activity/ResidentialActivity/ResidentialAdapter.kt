package com.topcutlawn.Activity.ResidentialActivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.topcutlawn.Activity.PropertyManagement.PropertyManagement
import com.topcutlawn.R
import com.topcutlawn.Utils.CommonUtils
import com.topcutlawn.databinding.RequestServicesBinding

class ResidentialAdapter(
    var residentiallist:  ArrayList<ResidentialViewModel.Data>,
    val mContext: Context,


) : RecyclerView.Adapter<ResidentialAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RequestServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RequestServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(residentiallist[position]) {

                holder.binding.tvName.setText(residentiallist[position].name)

                val url = residentiallist[position].image.replace("http:", "https:")
                Picasso.with(mContext).load(url).into(holder.binding.ivvideolistqigong)

                   binding.llHome.setOnClickListener {
                       val mainIntent = Intent(mContext, PropertyManagement::class.java)
                       mainIntent.putExtra("service_id",residentiallist[position].id)
                       mainIntent.putExtra("name",residentiallist[position].name)
                       mContext.startActivity(mainIntent)
                   }
            }

        }
    }

    override fun getItemCount(): Int {
        return residentiallist.size
    }
}