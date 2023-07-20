package com.topcutlawn.HomeFragment.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.Activity.GalleryActivity.GalleryActivity
import com.topcutlawn.HomeFragment.HomeViewModel
import com.topcutlawn.databinding.GalleryBinding

class GalleryAdapter(
    var gallerylist: List<HomeViewModel.GalleryViewModel>,
    val mContext: Context,
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GalleryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(gallerylist[position]) {
                binding.tvName.text = this.Grass

            }
    /*        binding.llGallery.setOnClickListener {
                val mainIntent = Intent(mContext, GalleryActivity::class.java)
                mContext.startActivity(mainIntent)
            }*/
        }
    }

    override fun getItemCount(): Int {
        return gallerylist.size
    }
}