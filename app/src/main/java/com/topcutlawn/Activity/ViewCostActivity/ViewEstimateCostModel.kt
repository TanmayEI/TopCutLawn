package com.topcutlawn.Activity.ViewCostActivity

data class ViewEstimateCostModel(
    val cost: String,
    val `data`: List<Data>,
    val message: String,
    val status: String
){
    data class Data(
        val message: String,
        val services: String
    )
}