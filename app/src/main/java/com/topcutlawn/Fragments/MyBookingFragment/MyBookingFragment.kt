package com.topcutlawn.Fragments.MyBookingFragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Fragments.InvoiceListFragment.InvoiceListFragment
import com.topcutlawn.R
import com.topcutlawn.SecondFragment
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentMyBookingBinding
import retrofit2.Response


class MyBookingFragment : Fragment(),MyBookingNavigator {

    private var _binding: FragmentMyBookingBinding? = null

    private val binding get() = _binding!!
    private var mAppLoader: AppLoader? = null
    private lateinit var ongoingAdapter: MyBookingAdapter
    private lateinit var ongoinglist: List<MyBookingModel>
    var user_id = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mAppLoader = AppLoader(requireActivity() as Activity?)
        user_id =
            SharedPreferenceUtils.getInstance(requireContext()).getStringValue(ConstantUtils.ID, "")
                .toString()

        my_booking("1")



        binding.tvongogin.setOnClickListener {
            binding.tvpastbooking.setBackgroundResource(R.drawable.button_corner)
            binding.tvpastbooking.setTextColor(Color.parseColor("#559C43"))
//            tvWorking.setTextColor(R.color.blue)
            binding.tvongogin.setBackgroundResource(R.drawable.button_booking_background);
            binding.tvongogin.setTextColor(Color.parseColor("#FFFFFFFF"))

            my_booking("1")
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
            my_booking("2")
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

    fun my_booking(status:String) {
         mAppLoader?.showDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)
        stringStringHashMap.put("status", status)


        var registration = APIUtils.getServiceAPI()!!.booking_list(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<MyBookingModel> {
            override fun onResponse(
                call: retrofit2.Call<MyBookingModel>,
                response: Response<MyBookingModel>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {
                            binding.rvOngoing.visibility=View.VISIBLE
                            mAppLoader?.dismissDialog()

                            binding.rvOngoing.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvOngoing.layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            ongoingAdapter = MyBookingAdapter(response.body()!!.data,this@MyBookingFragment,requireContext())

                            binding.rvOngoing.adapter = ongoingAdapter


                        } else {

                            binding.rvOngoing.visibility=View.GONE

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

            override fun onFailure(call: retrofit2.Call<MyBookingModel>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }

    override fun onClick(id: String) {
         val ldf = InvoiceListFragment()
        val args = Bundle()
        args.putString("booking_id", id)
        ldf.setArguments(args)

//Inflate the fragment

//Inflate the fragment
        getFragmentManager()?.beginTransaction()?.replace(R.id.framelayout_home, ldf)?.addToBackStack(null)?.commit()
    }


}