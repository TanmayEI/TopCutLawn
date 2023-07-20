package com.topcutlawn.Activity.NotificationActivity

data class NotificationViewModel(
    val `data`: List<Data>,
    val message: String,
    val status: String
){
    data class Data(
        val id: String,
        val title: String,
        val message: String,
        val status: String,
        val time: String)
}