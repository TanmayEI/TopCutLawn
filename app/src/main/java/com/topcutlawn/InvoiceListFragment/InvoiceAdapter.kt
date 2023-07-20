package com.topcutlawn.InvoiceListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.databinding.InvoiceListBinding

class InvoiceAdapter (
    var invoicelist: List<InvoiceViewModel>,
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
        with(holder) {
            with(invoicelist[position]) {
                binding.tvTime.text = this.Time

                /*         binding.llMsg.setOnClickListener {
                             val mainIntent = Intent(mContext, ChatActivity::class.java)
                             mContext.startActivity(mainIntent)
                         }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return invoicelist.size
    }
}