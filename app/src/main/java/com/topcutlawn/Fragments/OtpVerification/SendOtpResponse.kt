package com.topcutlawn.Fragments.OtpVerification

data class SendOtpResponse(
    val message: String,
    val status: String
){
    data class ResendOtp(
        val message: String,
        val otp: Int,
        val status: String
    )
}