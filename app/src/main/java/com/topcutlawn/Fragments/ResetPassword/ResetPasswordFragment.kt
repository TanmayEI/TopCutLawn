package com.topcutlawn.Fragments.ResetPassword

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.topcutlawn.API.APIUtils
import com.topcutlawn.R
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.NetworkUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentResetPasswordBinding
import com.topcutlawn.databinding.FragmentSecondBinding
import retrofit2.Response

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private var mAppLoader: AppLoader? = null
    var email = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var eyepass:Int=0
    var eyeconfirmpass:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        email =
            SharedPreferenceUtils.getInstance(requireContext()).getStringValue(ConstantUtils.EMAIL, "").toString()
        binding.tvCnfmpass.setOnClickListener {
            initViews()
        }

        binding.eyePass.setOnClickListener {
            if (eyepass==0){
                binding.eyePass.setImageResource(R.drawable.eye)
                binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
               // binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                binding.etPassword.setSelection(binding.etPassword.length())
                eyepass=1
            }else{
                binding.eyePass.setImageResource(R.drawable.eye_icon)
                binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
              //  binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.etPassword.setSelection(binding.etPassword.length())
                eyepass=0

            }


        }

        binding.eyeConfirmPass.setOnClickListener {
            if (eyeconfirmpass==0){
                binding.eyeConfirmPass.setImageResource(R.drawable.eye)
                binding.etCnfmpasswrod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                // binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                binding.etCnfmpasswrod.setSelection(binding.etCnfmpasswrod.length())
                eyeconfirmpass=1
            }else{
                binding.eyeConfirmPass.setImageResource(R.drawable.eye_icon)
                binding.etCnfmpasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //  binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.etCnfmpasswrod.setSelection(binding.etCnfmpasswrod.length())
                eyeconfirmpass=0

            }


        }

        return binding.root


    }

    fun initViews() {
            if (binding.etPassword.text.toString().isNullOrEmpty()) {
                binding.etPassword.error = "Please Enter New Password"
            } else if (binding.etCnfmpasswrod.text.toString().isNullOrEmpty()) {
                binding.etCnfmpasswrod.error = "Please Enter Confirm Password"
            }  else if (!binding.etCnfmpasswrod.text.toString().equals(binding.etCnfmpasswrod.text.toString())
            ) {
                Toast.makeText(
                    requireContext(), "New Password And Confirm Password Not Matched",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {

                if(NetworkUtils.checkInternetConnection(requireContext()))
                {
                    CreateNewPassword(
                        binding.etPassword.text.toString(),
                        binding.etCnfmpasswrod.text.toString(),
                        email.toString(),

                    )
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }

    /*    binding.tvCnfmpass.setOnClickListener {
            findNavController().navigate(R.id.action_ResetPasswordFragment_to_FirstFragment)
        }*/
    }

    fun CreateNewPassword(new_password: String, confirm_password: String, email: String) {
        mAppLoader?.showDialog()
        //binding.rlLoader!!.visibility= View.VISIBLE


        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("new_password", new_password)
        stringStringHashMap.put("confirm_password", confirm_password)
        stringStringHashMap.put("email", email)

        var SendForgetPassword = APIUtils.getServiceAPI()!!.resetpassword(stringStringHashMap)
        SendForgetPassword.enqueue(object : retrofit2.Callback<ResetPasswordResponse> {
            override fun onResponse(
                call: retrofit2.Call<ResetPasswordResponse>,
                response: Response<ResetPasswordResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.dismissDialog()
                        //binding.rlLoader!!.visibility= View.GONE

                        if (response.body()!!.status == "1")  {

                            findNavController().navigate(R.id.action_ResetPasswordFragment_to_FirstFragment)

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {
                            mAppLoader?.dismissDialog()
                            //binding.rlLoader!!.visibility= View.GONE

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
                    // binding.rlLoader!!.visibility= View.GONE


                }

            }

            override fun onFailure(call: retrofit2.Call<ResetPasswordResponse>, t: Throwable) {
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