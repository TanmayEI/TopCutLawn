package com.topcutlawn.Fragments.HomeFragment.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.Activity.GalleryActivity.GalleryActivity
import com.topcutlawn.Activity.PropertyManagement.PropertyManagement
import com.topcutlawn.Activity.ResidentialActivity.ResidentialActivity
import com.topcutlawn.Fragments.HomeFragment.HomeViewModel
import com.topcutlawn.databinding.RequestServicesBinding

class HomeAdapter  (
    var homedetailslist: List<HomeViewModel>,
    val mContext: Context,
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RequestServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RequestServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(homedetailslist[position]) {
                binding.tvName.text = this.CarName

             /*   binding.llHome.setOnClickListener {
                    val mainIntent = Intent(mContext, PropertyManagement::class.java)
                    mContext.startActivity(mainIntent)
                }*/
         /*       binding.llBlog.setOnClickListener {
                    val mainIntent = Intent(mContext, VehicleDetailsActivity::class.java)
                    mContext.startActivity(mainIntent)
                }*/
//                binding.ivFavourite.setImageResource(R.drawable.favorite)

            }
            binding.llHome.setOnClickListener {
                val mainIntent = Intent(mContext, ResidentialActivity::class.java)
                mContext.startActivity(mainIntent)
            }

        }
    }

    override fun getItemCount(): Int {
        return homedetailslist.size
    }
}