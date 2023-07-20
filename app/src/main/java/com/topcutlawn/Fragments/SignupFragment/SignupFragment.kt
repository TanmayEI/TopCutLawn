package com.topcutlawn.Fragments.SignupFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.Fragments.SignupFragment.SignupResponse.SignupResponse
import com.topcutlawn.MainActivity
import com.topcutlawn.R
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.NetworkUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentSignupBinding
import com.topcutlawn.databinding.FragmentSignupHomeBinding
import retrofit2.Response
import java.util.HashMap
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private var mAppLoader: AppLoader? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }
        binding.tvLogin.setOnClickListener {
            var intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

      /*  binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_SignupFragment_to_SignupHomeFragment)
        }*/
        /*    binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }*/
    }


    fun initViews() {
        binding.signup.setOnClickListener {
           // findNavController().navigate(R.id.action_SignupFragment_to_SignupHomeFragment)
            val add = binding.etEmail.text.toString().trim()
            if (binding.etName.text.toString().isNullOrEmpty()) {
                binding.etName.error = "Enter your UserName"
            } else if (binding.etEmail.text.toString().isNullOrEmpty()) {
                binding.etEmail.error = "Enter your Email"
            } else if(!emailValidator(add)){
                Toast.makeText(activity,"Please Enter Vaild Email Id", Toast.LENGTH_SHORT).show()
            } else if (binding.etPass.text.toString().isNullOrEmpty()) {
                binding.etPass.error = "Enter your Password"
            } else if (binding.etCnfmpass.text.toString().length<6) {
                binding.etCnfmpass.error = "Password Must be at least 6 Characters"
            } else if (!binding.etPass.text.toString()
                    .equals(binding.etCnfmpass.text.toString())
            ) {
                Toast.makeText(
                    requireContext(),
                    "Password And Confirm Password Not Matched",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {

                if(NetworkUtils.checkInternetConnection(requireContext()))
                {

                    registernew(
                        binding.etName.text.toString(),
                        binding.etPhone.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etAddress.text.toString(),
                        binding.etPass.text.toString(),

                    )
                }


            }
        }
    }

    fun registernew(name: String, phone: String, email: String,  address: String, password: String) {
        mAppLoader?.showDialog()
        // mAppLoader?.dismissDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("name", name)

        stringStringHashMap.put("mobile", phone)

        stringStringHashMap.put("email", email)

        stringStringHashMap.put("address", address)

        stringStringHashMap.put("password", password)

        stringStringHashMap.put("firebase_token", "123456")


        var registration = APIUtils.getServiceAPI()!!.registernew(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<SignupResponse> {
            override fun onResponse(
                call: retrofit2.Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()
                            findNavController().navigate(R.id.action_SignupFragment_to_SignupHomeFragment)


                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.USERNAME, name).toString()
                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.PHONE, phone).toString()
                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.EMAIL, email).toString()
                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.ADDRESS, address).toString()


                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {

                            //  binding.rlLoader.visibility = View.GONE
                            mAppLoader?.dismissDialog()
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        //binding.rlLoader.visibility = View.GONE
                        mAppLoader?.dismissDialog()
                        // dialog.dismiss()

                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    //  binding.rlLoader.visibility = View.GONE
                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(call: retrofit2.Call<SignupResponse>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }


    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}