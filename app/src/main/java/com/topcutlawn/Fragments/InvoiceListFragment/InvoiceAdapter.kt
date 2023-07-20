package com.topcutlawn.Fragments.InvoiceListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.databinding.InvoiceListBinding

class InvoiceAdapter(
    var invoicelist: InvoiceViewModel.Data,
    val mContext: Context,

    ) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: InvoiceListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            InvoiceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvTime.text = invoicelist.schedule_date_time
    }

    override fun getItemCount(): Int {
        return 1
    }
}