package com.topcutlawn.Activity.PropertyManagement

data class PropertyModel(
    val `data`: List<Data>,
    val message: String,
    val services_list: List<Data.Services>,
    val status: String
){
    data class Data(
        val capabilities: String,
        val commitment: String,
        val description: String,
        val id: String,
        val image: String,
        val name: String,
        val qualification: String
    ){
        data class Services(
            val services: String
        )
    }
}