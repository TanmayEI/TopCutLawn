package com.topcutlawn.Fragments.ProfileFragment

class PropertyPictureViewModel (
    val CarName : String ="",
    val BrandName : String =""
){
    data class EditProfileResponse(
        val message: String,
        val status: String,
        val user_details: UserDetails
    ){
        data class UserDetails(
            val address: String,
            val dimension_lawn: String,
            val dob: String,
            val email: String,
            val image: String,
            val mobile: String,
            val name: String,
            val total_square_lawn: String,
            val user_id: String,
            val virtual_pictures: List<VirtualPicture>
        )
        data class VirtualPicture(
            val vp_created_at: String,
            val vp_id: String,
            val vp_image: String,
            val vp_user_id: String
        )
    }
    data class UpdateProfileResponse(
        val message: String,
        val status: String
    )

    data class UpdateProfileImageResponse(
        val message: String,
        val status: String
    )
}