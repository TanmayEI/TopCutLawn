package com.topcutlawn.Fragments.HomeFragment

class HomeViewModel (
    val CarName : String ="",
    val BrandName : String ="",
    val Price : String ="",
    val image : String =""
)

{
    class GalleryViewModel (
    val Grass : String =""
    )

    data class HomeResponse(
        val `data`: List<Data>,
        val message: String,
        val status: String
    ){
        data class Data(
            val id: String,
            val image: String
        )
    }
}

