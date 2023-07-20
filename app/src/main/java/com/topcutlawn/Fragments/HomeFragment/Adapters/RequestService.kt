package com.topcutlawn.Fragments.HomeFragment.Adapters

data class RequestService(
    val `data`: List<Data>,
    val message: String,
    val status: String
){
    data class Data(
        val id: String,
        val image: String,
        val name: String
    )
}