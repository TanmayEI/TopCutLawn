package com.topcutlawn.Activity.ResidentialActivity

class ResidentialViewModel (
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