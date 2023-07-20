package com.topcutlawn.MyBookingFragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.HomeFragment.Adapters.HomeAdapter
import com.topcutlawn.HomeFragment.HomeViewModel
import com.topcutlawn.R
import com.topcutlawn.databinding.FragmentMyBookingBinding
import com.topcutlawn.databinding.FragmentSecondBinding

class MyBookingFragment : Fragment() {

    private var _binding: FragmentMyBookingBinding? = null

    private val binding get() = _binding!!

    private lateinit var ongoingAdapter: OngoingAdapter
    private lateinit var ongoinglist: List<OngoingViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Ongoinglist()

        binding.rvOngoing.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOngoing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        ongoingAdapter = OngoingAdapter(ongoinglist,requireContext())

        binding.rvOngoing.adapter = ongoingAdapter


        binding.tvongogin.setOnClickListener {
            binding.tvpastbooking.setBackgroundResource(R.drawable.button_corner)
            binding.tvpastbooking.setTextColor(Color.parseColor("#559C43"))
//            tvWorking.setTextColor(R.color.blue)
            binding.tvongogin.setBackgroundResource(R.drawable.button_booking_background);
            binding.tvongogin.setTextColor(Color.parseColor("#FFFFFFFF"))


/*
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.framelayout_mysubscription, CompletedFragment())
                .commit()*/


        }

        binding.tvpastbooking.setOnClickListener {
            binding.tvpastbooking.setBackgroundResource(R.drawable.button_booking_background)
            binding.tvpastbooking.setTextColor(Color.parseColor("#FFFFFFFF"))

            binding.tvongogin.setBackgroundResource(R.drawable.button_corner);
            binding.tvongogin.setTextColor(Color.parseColor("#559C43"))
//            tvCompleted.setTextColor(R.color.blue)

/*            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.framelayout_mysubscription, WorkingFragment())
                .commit()*/

        }

   /*     binding.sendOtp.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_OtpFragment)
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun Ongoinglist() {
        ongoinglist = listOf(
            OngoingViewModel(
                "Property Management",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
            OngoingViewModel(
                "Landscape Design",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
            OngoingViewModel(
                "Mulch and Rock Beds",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
            OngoingViewModel(
                "Property Management",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
            OngoingViewModel(
                "Property Management",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
            OngoingViewModel(
                "Property Management",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
            OngoingViewModel(
                "Property Management",
                "\$330",
                "05 jan 2023 - 12:35pm",
            ),
        )
    }
}