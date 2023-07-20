package com.topcutlawn.Activity.GalleryActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.topcutlawn.Fragments.HomeFragment.Adapters.GalleryBeforeAfterModel
import com.topcutlawn.databinding.GalleryBinding
import com.topcutlawn.databinding.GalleryViewAllBinding

class GalleryAdapter (
    var gallerylist: List<GalleryBeforeAfterModel.Data>,
    val mContext: Context,

    ) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GalleryViewAllBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GalleryViewAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvName.setText(gallerylist[position].name)
        val url = gallerylist[position].image.replace("http:", "https:")
        Picasso.with(mContext).load(url).into(holder.binding.ivvideolistqigong)
    }

    override fun getItemCount(): Int {
        return gallerylist.size
    }
}