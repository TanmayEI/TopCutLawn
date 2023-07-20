package com.topcutlawn.Fragments.MyBookingFragment

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.topcutlawn.Fragments.InvoiceListFragment.InvoiceListFragment
import com.topcutlawn.databinding.OngoingBookingBinding


class MyBookingAdapter (
    var my_booking: List<MyBookingModel.Data>,
    val myBookingNavigator: MyBookingNavigator,
    val mContext: Context,

    ) : RecyclerView.Adapter<MyBookingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: OngoingBookingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OngoingBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvName.text = my_booking[position].service_name
        holder.binding.tvPrice.text =  my_booking[position].service_price
        holder.binding.tvTime.text =  my_booking[position].booking_date

        val url = my_booking[position].service_image.replace("http:", "https:")
        Picasso.with(mContext).load(url).into(holder.binding.myBookingImage)

        holder.binding.llViewinvoice.setOnClickListener {
            myBookingNavigator.onClick(my_booking[position].booking_id)
         //   onClick(holder.itemView)

        }
    }

    override fun getItemCount(): Int {
        return my_booking.size
    }
    fun onClick(view: View) {

        val ldf = InvoiceListFragment()


        val activity = view.getContext() as AppCompatActivity
        val invoiceListFragment: Fragment = InvoiceListFragment()
        activity.supportFragmentManager.beginTransaction()
            .replace(com.topcutlawn.R.id.framelayout_home, invoiceListFragment).
            addToBackStack(null).commit()

    }
}