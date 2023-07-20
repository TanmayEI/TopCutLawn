package com.topcutlawn

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
import com.topcutlawn.Fragments.OtpVerification.OtpFragment
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentSecondBinding
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var mAppLoader: AppLoader? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }

        binding.sendOtp.setOnClickListener {
            SendForgetPassword(
                binding.etemailaddress.text.toString()
            )
//            findNavController().navigate(R.id.action_SecondFragment_to_OtpFragment)
        }
    }


    fun SendForgetPassword(email: String) {
        mAppLoader?.showDialog()
//        binding.rlLoader!!.visibility= View.VISIBLE


        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("email", email)

        var SendForgetPassword = APIUtils.getServiceAPI()!!.RestPassword(stringStringHashMap)
        SendForgetPassword.enqueue(object : retrofit2.Callback<ForgotPasswordResponse> {
            override fun onResponse(
                call: retrofit2.Call<ForgotPasswordResponse>,
                response: Response<ForgotPasswordResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.dismissDialog()
                        // binding.rlLoader!!.visibility= View.GONE

                        if (response.body()!!.status == "1") {
                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.EMAIL,email)


                            findNavController().navigate(R.id.action_SecondFragment_to_OtpFragment)

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

            override fun onFailure(call: retrofit2.Call<ForgotPasswordResponse>, t: Throwable) {
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