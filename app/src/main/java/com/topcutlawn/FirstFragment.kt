package com.topcutlawn

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
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.Fragments.SignupFragment.SignupResponse.SignupResponse
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.NetworkUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentFirstBinding
import retrofit2.Response
import java.util.HashMap
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var mAppLoader: AppLoader? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.tvforgotpassword.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
           // findNavController().navigate(R.id.ResetPasswordFragment)
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SignupFragment)
        }

        binding.ivMenu.setOnClickListener {
            getActivity()?.moveTaskToBack(true);
            getActivity()?.finish();
        }

        /*  binding.tvLogin.setOnClickListener {
              //findNavController().navigate(R.id.action_FirstFragment_to_HomeFragment)
              var intent = Intent(context, HomeActivity::class.java)
              startActivity(intent)
          }*/

        /*    binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }*/
    }


    fun initViews() {
        binding.tvLogin.setOnClickListener {
            val add = binding.etEmail.text.toString().trim()
            if (binding.etEmail.text.toString().isNullOrEmpty()) {
                binding.etEmail.error = "Enter your Email"
            } else if (binding.etPassword.text.toString().isNullOrEmpty()) {
                binding.etPassword.error = "Enter your Password"
            } else if (!emailValidator(add)) {
                Toast.makeText(activity, "Please Enter Vaild Email Id", Toast.LENGTH_SHORT).show()
            } else {

                if (NetworkUtils.checkInternetConnection(requireContext())) {
                    login(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                }


            }
        }
    }

    fun login(email: String, password: String) {
        mAppLoader?.showDialog()
        // mAppLoader?.dismissDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("email", email)

        stringStringHashMap.put("password", password)


        var registration = APIUtils.getServiceAPI()!!.login(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {
                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.IS_LOGIN, "true").toString()

                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.ID,response.body()!!.user_detail.id)

                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.USERNAME,response.body()!!.user_detail.name)




                            var intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)

                            onDestroy()

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

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
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