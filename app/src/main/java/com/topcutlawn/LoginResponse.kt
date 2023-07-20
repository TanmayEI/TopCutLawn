package com.topcutlawn

data class LoginResponse(
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