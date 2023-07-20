package com.topcutlawn.Fragments.HomeFragment.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.topcutlawn.Activity.GalleryActivity.GalleryActivity
import com.topcutlawn.Activity.PropertyManagement.PropertyManagement
import com.topcutlawn.Activity.ResidentialActivity.ResidentialActivity
import com.topcutlawn.Fragments.HomeFragment.HomeViewModel
import com.topcutlawn.databinding.RequestServicesBinding

class RequestServiceAdapter  (
    var homedetailslist: List<RequestService.Data>,
    val mContext: Context,
) : RecyclerView.Adapter<RequestServiceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RequestServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RequestServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvName.text = homedetailslist[position].name

        val url = homedetailslist[position].image.replace("http:", "https:")
        Picasso.with(mContext).load(url).into(holder.binding.ivvideolistqigong)

        holder.binding.llHome.setOnClickListener {
            val mainIntent = Intent(mContext, ResidentialActivity::class.java)
            mainIntent.putExtra("name",homedetailslist[position].name)
            mContext.startActivity(mainIntent)
        }



    }

    override fun getItemCount(): Int {
        return homedetailslist.size
    }
}