package com.topcutlawn.Fragments.MyBookingFragment

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.Fragments.InvoiceListFragment.InvoiceListFragment
import com.topcutlawn.databinding.OngoingBookingBinding


class OngoingAdapter (
    var ongoinglist: List<MyBookingModel>,
    val mContext: Context,

    ) : RecyclerView.Adapter<OngoingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: OngoingBookingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OngoingBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(ongoinglist[position]) {
                /*binding.tvName.text = this.Name
                binding.tvPrice.text = this.Money
                binding.tvTime.text = this.Time*/

            }


            binding.llViewinvoice.setOnClickListener {
                onClick(itemView)

            }
        }
    }

    override fun getItemCount(): Int {
        return ongoinglist.size
    }
    fun onClick(view: View) {
        val activity = view.getContext() as AppCompatActivity
        val invoiceListFragment: Fragment = InvoiceListFragment()
        activity.supportFragmentManager.beginTransaction()
            .replace(com.topcutlawn.R.id.framelayout_home, invoiceListFragment).addToBackStack(null).commit()

    }
}