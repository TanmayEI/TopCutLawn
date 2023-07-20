package com.topcutlawn.OtpVerification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.topcutlawn.R
import com.topcutlawn.databinding.FragmentFirstBinding
import com.topcutlawn.databinding.FragmentOtpBinding
import com.topcutlawn.databinding.FragmentSecondBinding

class OtpFragment : Fragment(){

    private var _binding: FragmentOtpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }

        binding.tvVerifynow.setOnClickListener {
            findNavController().navigate(R.id.action_OtpFragment_to_ResetPasswordFragment)
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