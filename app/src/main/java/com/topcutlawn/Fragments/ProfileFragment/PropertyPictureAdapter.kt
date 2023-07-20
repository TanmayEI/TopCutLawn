package com.topcutlawn.Fragments.ProfileFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.topcutlawn.R
import com.topcutlawn.Utils.CommonUtils
import com.topcutlawn.databinding.PicturepropertyesBinding
import java.util.ArrayList

class PropertyPictureAdapter(
    val mContext: Context,
    val picturelist: List<PropertyPictureViewModel.EditProfileResponse.VirtualPicture>,

) : RecyclerView.Adapter<PropertyPictureAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: PicturepropertyesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PicturepropertyesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = picturelist[position]

        try {
            val url =data.vp_image.replace("http:", "https:")

            Picasso.with(mContext).load(url).into(holder.binding.ivFavourite)
        }catch (e:Exception){

        }

/*
        if (data.vp_image != null && !data.vp_image.equals("")) {
            CommonUtils.loadImageWithGlide(
                mContext, holder.binding.ivFavourite,
                data.vp_image
            )

        } else {

            holder.binding.ivFavourite.setImageResource(R.drawable.picturemanagement)
        }
*/

    }


    override fun getItemCount(): Int {
        return picturelist.size
    }
}
