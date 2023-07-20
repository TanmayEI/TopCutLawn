package com.topcutlawn.ProfileFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.databinding.GalleryBinding
import com.topcutlawn.databinding.PicturepropertyesBinding

class PropertyPictureAdapter (
    var picturelist: List<PropertyPictureViewModel>,
    val mContext: Context,
) : RecyclerView.Adapter<PropertyPictureAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PicturepropertyesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PicturepropertyesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(picturelist[position]) {
                //binding.tvName.text = this.CarName

            }
        }
    }

    override fun getItemCount(): Int {
        return picturelist.size
    }
}