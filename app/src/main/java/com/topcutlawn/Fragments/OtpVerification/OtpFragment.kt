package com.topcutlawn.Fragments.OtpVerification

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.topcutlawn.API.APIUtils
import com.topcutlawn.ForgotPasswordResponse
import com.topcutlawn.R
import com.topcutlawn.Utils.*
import com.topcutlawn.databinding.FragmentFirstBinding
import com.topcutlawn.databinding.FragmentOtpBinding
import com.topcutlawn.databinding.FragmentSecondBinding
import retrofit2.Response

class OtpFragment : Fragment(){

    private var _binding: FragmentOtpBinding? = null
    private var mAppLoader: AppLoader? = null
    var email = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }
        binding.tvResendcode.setOnClickListener {
            ResendOtp(
                SharedPreferenceUtils.getInstance(requireContext()).getStringValue(ConstantUtils.EMAIL, "").toString()
            )
//            findNavController().navigate(R.id.action_SecondFragment_to_OtpFragment)
        }

      /*  binding.tvVerifynow.setOnClickListener {
            findNavController().navigate(R.id.action_OtpFragment_to_ResetPasswordFragment)
        }*/
        /*    binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }*/
    }



    fun initViews() {
        binding.tvVerifynow.setOnClickListener {
            if (binding.tvVerifynow.text.toString().isEmpty()) {
                ToastUtil.toast_Long(requireContext(), resources.getString(R.string.enterotp))
            } else {
                if(NetworkUtils.checkInternetConnection(requireContext()))
                    VerifyOtp(
                        SharedPreferenceUtils.getInstance(requireContext())
                            ?.getStringValue(ConstantUtils.EMAIL, "").toString(),
                        binding.pvOtp.text.toString(),

                        )

            }
        }
    }

    fun ResendOtp(email: String) {
        mAppLoader?.showDialog()
//        binding.rlLoader!!.visibility= View.VISIBLE


        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("email", email)

        var SendForgetPassword = APIUtils.getServiceAPI()!!.resendotp(stringStringHashMap)
        SendForgetPassword.enqueue(object : retrofit2.Callback<SendOtpResponse.ResendOtp> {
            override fun onResponse(
                call: retrofit2.Call<SendOtpResponse.ResendOtp>,
                response: Response<SendOtpResponse.ResendOtp>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.dismissDialog()
                        // binding.rlLoader!!.visibility= View.GONE

                        if (response.body()!!.status == "1") {

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {
                            mAppLoader?.dismissDialog()
                            // binding.rlLoader!!.visibility= View.GONE
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()
                        //binding.rlLoader!!.visibility= View.GONE

                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    mAppLoader?.dismissDialog()
                    //binding.rlLoader!!.visibility= View.GONE

                }

            }

            override fun onFailure(call: retrofit2.Call<SendOtpResponse.ResendOtp>, t: Throwable) {
                mAppLoader?.dismissDialog()
                //binding.rlLoader!!.visibility= View.GONE

                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }

    fun VerifyOtp(email: String, otp: String) {
        mAppLoader?.showDialog()
        //binding.rlLoader!!.visibility= View.VISIBLE


        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("email", email)
        stringStringHashMap.put("otp", otp)

        var SendForgetPassword = APIUtils.getServiceAPI()!!.verifyotp(stringStringHashMap)
        SendForgetPassword.enqueue(object : retrofit2.Callback<SendOtpResponse> {
            override fun onResponse(
                call: retrofit2.Call<SendOtpResponse>,
                response: Response<SendOtpResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.dismissDialog()
                        //binding.rlLoader!!.visibility= View.GONE

                        if (response.body()!!.status == "1") {

                            findNavController().navigate(R.id.action_OtpFragment_to_ResetPasswordFragment)

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {
                            mAppLoader?.dismissDialog()
                            // binding.rlLoader!!.visibility= View.GONE
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()
                        //binding.rlLoader!!.visibility= View.GONE

                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    mAppLoader?.dismissDialog()
                    //binding.rlLoader!!.visibility= View.GONE

                }

            }

            override fun onFailure(call: retrofit2.Call<SendOtpResponse>, t: Throwable) {
                mAppLoader?.dismissDialog()
                //binding.rlLoader!!.visibility= View.GONE

                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}