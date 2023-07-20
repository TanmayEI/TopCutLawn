package com.topcutlawn.SignupFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.topcutlawn.HomeFragment.HomeActivity
import com.topcutlawn.MainActivity
import com.topcutlawn.R
import com.topcutlawn.databinding.FragmentSignupBinding
import com.topcutlawn.databinding.FragmentSignupHomeBinding


class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }
        binding.tvLogin.setOnClickListener {
            var intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_SignupFragment_to_SignupHomeFragment)
        }
        /*    binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}