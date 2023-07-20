package com.topcutlawn.Fragments.HomeFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarteist.autoimageslider.SliderView
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.GalleryActivity.GalleryActivity
import com.topcutlawn.Fragments.HomeFragment.Adapters.*
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentHomeBinding
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mAppLoader: AppLoader? = null
    var user_id = ""
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

    private lateinit var homeAdapter: RequestServiceAdapter
    private lateinit var homedetailslist: List<HomeViewModel>
    private lateinit var galleryAdapter: GalleryAdapter

    private lateinit var gallerylist: List<HomeViewModel.GalleryViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        user_id =
            SharedPreferenceUtils.getInstance(requireContext()).getStringValue(ConstantUtils.ID, "")
                .toString()

        // on below line we are initializing our slier view.
        //sliderView = findViewById(R.id.slider)

        // on below line we are initializing
        // our image url array list.

        banner()
        request_service()
        gallery_before_after()


        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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

    private fun slide(image1: ArrayList<String>?) {



        sliderAdapter = SliderAdapter(requireActivity(),image1!!)

        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        binding.slider.setSliderAdapter(sliderAdapter)

        binding.slider.scrollTimeInSec = 3

        binding.slider.isAutoCycle = true

        binding.slider.startAutoCycle()
    }

    fun banner() {
        mAppLoader?.showDialog()
        // mAppLoader?.dismissDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)


        var registration = APIUtils.getServiceAPI()!!.banner(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<HomeViewModel.HomeResponse> {
            override fun onResponse(
                call: retrofit2.Call<HomeViewModel.HomeResponse>,
                response: Response<HomeViewModel.HomeResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {
                            /*imageUrl = ArrayList()
                            for (i in 0 until response.body()!!.data.size)
                                imageUrl.add((response.body()!!.data[i].image) )




                            // on below line we are adding data to our image url array list.

                            // on below line we are initializing our
                            // slider adapter and adding our list to it.
                            sliderAdapter = SliderAdapter( requireActivity(),imageUrl)

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
                            binding.slider.startAutoCycle()*/
                            mAppLoader?.dismissDialog()
                            val image = ArrayList<String>()
                            val size=response.body()!!.data.size
                            for (i in 0 until size )
                                    image?.add( (response.body()!!.data[i].image))

                            slide(
                                image
                            )



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

            override fun onFailure(call: retrofit2.Call<HomeViewModel.HomeResponse>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }


    fun request_service() {
        // mAppLoader?.dismissDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)


        var registration = APIUtils.getServiceAPI()!!.request_service(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<RequestService> {
            override fun onResponse(
                call: retrofit2.Call<RequestService>,
                response: Response<RequestService>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {

                            mAppLoader?.dismissDialog()
                            binding.rvRequestservices.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvRequestservices.layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            homeAdapter = RequestServiceAdapter(response.body()!!.data,requireContext())

                            binding.rvRequestservices.adapter = homeAdapter


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

            override fun onFailure(call: retrofit2.Call<RequestService>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }

    fun gallery_before_after() {
        // mAppLoader?.dismissDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)


        var registration = APIUtils.getServiceAPI()!!.gallery_before_after(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<GalleryBeforeAfterModel> {
            override fun onResponse(
                call: retrofit2.Call<GalleryBeforeAfterModel>,
                response: Response<GalleryBeforeAfterModel>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {

                            mAppLoader?.dismissDialog()
                            binding.rvGallery.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvGallery.layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            galleryAdapter = GalleryAdapter(response.body()!!.data,requireContext())

                            binding.rvGallery.adapter = galleryAdapter


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

            override fun onFailure(call: retrofit2.Call<GalleryBeforeAfterModel>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
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