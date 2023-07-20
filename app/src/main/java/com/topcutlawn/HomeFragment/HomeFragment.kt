package com.topcutlawn.HomeFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarteist.autoimageslider.SliderView
import com.topcutlawn.Activity.ComercialActivity.ComercialActivity
import com.topcutlawn.Activity.GalleryActivity.GalleryActivity
import com.topcutlawn.Activity.ResidentialActivity.ResidentialActivity
import com.topcutlawn.HomeFragment.Adapters.GalleryAdapter
import com.topcutlawn.HomeFragment.Adapters.HomeAdapter
import com.topcutlawn.HomeFragment.Adapters.SliderAdapter
import com.topcutlawn.R
import com.topcutlawn.databinding.FragmentFirstBinding
import com.topcutlawn.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // on below line we are creating a variable
    // for our array list for storing our images.
    lateinit var imageUrl: ArrayList<String>

    // on below line we are creating
    // a variable for our slider view.
    lateinit var sliderView: SliderView

    // on below line we are creating
    // a variable for our slider adapter.
    lateinit var sliderAdapter: SliderAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homedetailslist: List<HomeViewModel>

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var gallerylist: List<HomeViewModel.GalleryViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        // on below line we are initializing our slier view.
        //sliderView = findViewById(R.id.slider)

        // on below line we are initializing
        // our image url array list.
        imageUrl = ArrayList()

        // on below line we are adding data to our image url array list.
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdsa-self-paced-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdata-science-live-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Ffull-stack-node-thumbnail.png&w=1920&q=75") as ArrayList<String>

        // on below line we are initializing our
        // slider adapter and adding our list to it.
        sliderAdapter = SliderAdapter( imageUrl)

        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        binding.slider.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        binding.slider.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        binding.slider.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        binding.slider.startAutoCycle()

        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homedetailslist()

        binding.rvRequestservices.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRequestservices.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeAdapter = HomeAdapter(homedetailslist,requireContext())

        binding.rvRequestservices.adapter = homeAdapter

        //gallery adapter
        gallerylist()

        binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGallery.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        galleryAdapter = GalleryAdapter(gallerylist,requireContext())

        binding.rvGallery.adapter = galleryAdapter

    /*    binding.llHome.setOnClickListener {
            val mainIntent = Intent(requireContext(), ResidentialActivity::class.java)
            requireContext().startActivity(mainIntent)
        }

        binding.llHomecomercial.setOnClickListener {
            val mainIntent = Intent(requireContext(), ComercialActivity::class.java)
            requireContext().startActivity(mainIntent)
        }*/

        binding.tvViewall.setOnClickListener {
            val mainIntent = Intent(requireContext(), GalleryActivity::class.java)
            requireContext().startActivity(mainIntent)
        }

     /*   binding.tvClick.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_MyBookingFragment)
        }*/


    /*    binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SignupFragment)
        }*/

  /*      binding.tvLogin.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_HomeFragment)
            var intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }*/

        /*    binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun homedetailslist() {
        homedetailslist = listOf(
            HomeViewModel(
                "Residential Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Residential Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Residential Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Residential Services",
            ),
        )
    }

    fun gallerylist() {
        gallerylist = listOf(
            HomeViewModel.GalleryViewModel(
                "Fresh Cut Grass",
            ),
            HomeViewModel.GalleryViewModel(
                "Mulch and Rock Beds",
            ),
            HomeViewModel.GalleryViewModel(
                "Snow Plowing",
            ),
            HomeViewModel.GalleryViewModel(
                "Fresh Cut Grass",
            ),
            HomeViewModel.GalleryViewModel(
                "Fresh Cut Grass",
            ),
            HomeViewModel.GalleryViewModel(
                "Snow Plowing",
            ),
            HomeViewModel.GalleryViewModel(
                "Fresh Cut Grass",
            ),
        )
    }
}
















/*{
    lateinit var drawerLayout: DrawerLayout
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homedetailslist: List<HomeViewModel>

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var gallerylist: List<HomeViewModel.GalleryViewModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        homedetailslist()

        binding.rvRequestservices.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRequestservices.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeAdapter = HomeAdapter(homedetailslist,requireContext())

        binding.rvRequestservices.adapter = homeAdapter

        //gallery adapter
        gallerylist()

        binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGallery.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        galleryAdapter = GalleryAdapter(gallerylist,requireContext())

        binding.rvGallery.adapter = galleryAdapter

    *//*    binding.ivMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }*//*


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun homedetailslist() {
        homedetailslist = listOf(
            HomeViewModel(
                "Residential Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Residential Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Residential Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Residential Services",
            ),
        )
    }

    fun gallerylist() {
        gallerylist = listOf(
            HomeViewModel.GalleryViewModel(
                "Residential Services",
            ),
            HomeViewModel.GalleryViewModel(
                "Commercial Services",
            ),
            HomeViewModel.GalleryViewModel(
                "Residential Services",
            ),
            HomeViewModel.GalleryViewModel(
                "Commercial Services",
            ),
            HomeViewModel.GalleryViewModel(
                "Residential Services",
            ),
            HomeViewModel.GalleryViewModel(
                "Commercial Services",
            ),
            HomeViewModel.GalleryViewModel(
                "Residential Services",
            ),
        )
    }

}*/