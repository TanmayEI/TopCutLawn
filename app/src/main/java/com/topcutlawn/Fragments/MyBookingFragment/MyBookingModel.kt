package com.topcutlawn.Fragments.MyBookingFragment

data class MyBookingModel(
    val `data`: List<Data>,
    val message: String,
    val status: String
){
    data class Data(
        val booking_date: String,
        val booking_id: String,
        val service_image: String,
        val service_name: String,
        val service_price: String,
        val view_invoice: String
    )
}