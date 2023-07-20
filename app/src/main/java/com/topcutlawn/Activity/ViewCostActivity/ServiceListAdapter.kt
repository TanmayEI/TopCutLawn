package com.topcutlawn.Activity.ViewCostActivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.topcutlawn.Activity.GetCostActivity.GetCostNavigator
import com.topcutlawn.Activity.PropertyManagement.PropertyManagement
import com.topcutlawn.R
import com.topcutlawn.Utils.CommonUtils
import com.topcutlawn.databinding.RequestServicesBinding
import com.topcutlawn.databinding.ViewCostServicesBinding

class ServiceListAdapter(
    val mContext: Context,
    val service_list: ArrayList<String> = ArrayList(),
    var getCostNavigatior:GetCostNavigator


) : RecyclerView.Adapter<ServiceListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewCostServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewCostServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.text.setText(service_list[position])



        holder.binding.one.setOnClickListener {
            if (holder.binding.one.isChecked){
                // Toast.makeText(mContext,"check",Toast.LENGTH_SHORT).show()
                getCostNavigatior.onClick(service_list[position],position,"check")
            }else{
                //  Toast.makeText(mContext,"uncheck",Toast.LENGTH_SHORT).show()
                getCostNavigatior.onClick(service_list[position],position,"uncheck")
            }
            // getCostNavigatior.onClick(residentiallist[position].message)
        }
    }

    override fun getItemCount(): Int {
        return service_list.size
    }
}