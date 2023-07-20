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
import com.topcutlawn.databinding.ViewCostServicesDesignBinding

class ViewCostAdapter(
    val mContext: Context,
    var residentiallist:  List<ViewEstimateCostModel.Data>


) : RecyclerView.Adapter<ViewCostAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewCostServicesDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewCostServicesDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(residentiallist[position]) {

                holder.binding.text.setText(residentiallist[position].services)







            }

        }
    }

    override fun getItemCount(): Int {
        return residentiallist.size
    }
}