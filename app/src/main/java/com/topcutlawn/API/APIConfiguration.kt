package com.topcutlawn.API

import com.topcutlawn.Activity.GetCostActivity.GetEstimateModel
import com.topcutlawn.Activity.NotificationActivity.NotificationChangeStaus
import com.topcutlawn.Activity.NotificationActivity.NotificationViewModel
import com.topcutlawn.Activity.PropertyManagement.PropertyModel
import com.topcutlawn.Activity.ResidentialActivity.ResidentialViewModel
import com.topcutlawn.Activity.ScheduleActivity.ScheduleServiceModel
import com.topcutlawn.Activity.ViewContractActivity.BooNowModel
import com.topcutlawn.Activity.ViewContractActivity.PaypalModel
import com.topcutlawn.Activity.ViewCostActivity.ViewEstimateCostModel
import com.topcutlawn.ForgotPasswordResponse
import com.topcutlawn.Fragments.HomeFragment.Adapters.GalleryBeforeAfterModel
import com.topcutlawn.Fragments.HomeFragment.Adapters.RequestService
import com.topcutlawn.Fragments.HomeFragment.HomeViewModel
import com.topcutlawn.Fragments.InvoiceListFragment.InvoiceViewModel
import com.topcutlawn.Fragments.MyBookingFragment.MyBookingModel
import com.topcutlawn.Fragments.OtpVerification.SendOtpResponse
import com.topcutlawn.Fragments.ProfileFragment.PropertyPictureViewModel
import com.topcutlawn.Fragments.ResetPassword.ResetPasswordResponse
import com.topcutlawn.Fragments.SignupFragment.SignupResponse.SignupResponse
import com.topcutlawn.Fragments.SignupHomeFragment.UpdateVirtualImageModel
import com.topcutlawn.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIConfiguration {
    @POST("index.php/api/register")
    @Headers("Content-Type: application/json")
    fun registernew(
        @Body request: java.util.HashMap<String, String>
    ): Call<SignupResponse>

    @POST("api/login")
    @Headers("Content-Type: application/json")
    fun login(
        @Body request: java.util.HashMap<String, String>
    ): Call<LoginResponse>

    @POST("api/edit_profile")
    @Headers("Content-Type: application/json")
    fun editprofile(
        @Body request: java.util.HashMap<String, String>
    ): Call<PropertyPictureViewModel.EditProfileResponse>

    @POST("api/update_profile")
    @Headers("Content-Type: application/json")
    fun updateprofile(
        @Body request: java.util.HashMap<String, String>
    ): Call<PropertyPictureViewModel.UpdateProfileResponse>

    @Multipart
    @POST("api/update_profile_image")
    fun userimageupdate(
        @Part("user_id") userId: RequestBody,
        @Part image: MultipartBody.Part?,
    ): Call<PropertyPictureViewModel.UpdateProfileImageResponse>

    @Multipart
    @POST("api/book_now")
    fun book_now(
        @Part("user_id") userId: RequestBody,
        @Part("service_id") service_id: RequestBody,
        @Part("schedule_date") schedule_date: RequestBody,
        @Part("txn_id") txn_id: RequestBody,
        @Part("txn_amount") txn_amount: RequestBody,
        @Part("payment_type") payment_type: RequestBody,
        @Part document: MultipartBody.Part?,
    ): Call<BooNowModel>


    @Multipart
    @POST("api/update_virtual_picture")
    fun update_virtual_picture(
        @Part("user_id") userId: RequestBody,
        @Part image: MultipartBody.Part?,
    ): Call<UpdateVirtualImageModel>



    @POST("api/forgot_password")
    @Headers("Content-Type: application/json")
    fun RestPassword(
        @Body request: java.util.HashMap<String, String>
    ): Call<ForgotPasswordResponse>

    @POST("api/verify_otp")
    @Headers("Content-Type: application/json")
    fun verifyotp(
        @Body request: java.util.HashMap<String, String>
    ): Call<SendOtpResponse>

    @POST("api/resend_otp")
    @Headers("Content-Type: application/json")
    fun resendotp(
        @Body request: java.util.HashMap<String, String>
    ): Call<SendOtpResponse.ResendOtp>

    @POST("api/set_new_password")
    @Headers("Content-Type: application/json")
    fun resetpassword(
        @Body request: java.util.HashMap<String, String>
    ): Call<ResetPasswordResponse>

    @POST("api/banner")
    @Headers("Content-Type: application/json")
    fun banner(
        @Body request: java.util.HashMap<String, String>
    ): Call<HomeViewModel.HomeResponse>

    @POST("api/request_service")
    @Headers("Content-Type: application/json")
    fun request_service(
        @Body request: java.util.HashMap<String, String>
    ): Call<RequestService>

    @POST("api/gallery")
    @Headers("Content-Type: application/json")
    fun gallery_before_after(
        @Body request: java.util.HashMap<String, String>
    ): Call<GalleryBeforeAfterModel>

    @POST("api/notification")
    @Headers("Content-Type: application/json")
    fun notification(
        @Body request: java.util.HashMap<String, String>
    ): Call<NotificationViewModel>

    @POST("api/change_status_of_notification")
    @Headers("Content-Type: application/json")
    fun change_status_of_notification(
        @Body request: java.util.HashMap<String, String>
    ): Call<NotificationChangeStaus>


    @POST("api/booking_list")
    @Headers("Content-Type: application/json")
    fun booking_list(
        @Body request: java.util.HashMap<String, String>
    ): Call<MyBookingModel>

    @POST("api/booking_detail")
    @Headers("Content-Type: application/json")
    fun booking_detail(
        @Body request: java.util.HashMap<String, String>
    ): Call<InvoiceViewModel>


    @POST("api/service")
    @Headers("Content-Type: application/json")
    fun services(
        @Body request: java.util.HashMap<String, String>
    ): Call<ResidentialViewModel>

    @POST("api/service_detail")
    @Headers("Content-Type: application/json")
    fun service_detail(
        @Body request: java.util.HashMap<String, String>
    ): Call<PropertyModel>

    @POST("api/view_estimate_cost")
    @Headers("Content-Type: application/json")
    fun view_estimate_cost(
        @Body request: java.util.HashMap<String, String>
    ): Call<ViewEstimateCostModel>

    @POST("api/get_estimate_cost")
    @Headers("Content-Type: application/json")
    fun get_estimate_cost(
        @Body request: java.util.HashMap<String, String>
    ): Call<GetEstimateModel>

    @POST("api/schedule_service_date")
    @Headers("Content-Type: application/json")
    fun schedule_service_date(
        @Body request: java.util.HashMap<String, String>
    ): Call<ScheduleServiceModel>

    @POST("api/paypal")
    @Headers("Content-Type: application/json")
    fun paypal(
        @Body request: java.util.HashMap<String, String>
    ): Call<PaypalModel>





}
