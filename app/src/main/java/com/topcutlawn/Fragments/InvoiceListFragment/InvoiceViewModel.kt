package com.topcutlawn.Fragments.InvoiceListFragment

data class InvoiceViewModel(
    val `data`: Data,
    val message: String,
    val status: String
){
    data class Data(
        val schedule_date_time: String,
        val view_invoice: String
    )
}