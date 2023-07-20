package com.topcutlawn

data class ForgotPasswordResponse(
    val message: String,
    val status: String,
    val user_detail: UserDetail
){
    data class UserDetail(
        val address: String,
        val email: String,
        val id: String,
        val mobile: String,
        val name: String
    )
}