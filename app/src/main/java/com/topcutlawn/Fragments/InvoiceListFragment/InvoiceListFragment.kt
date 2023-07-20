package com.topcutlawn.Fragments.InvoiceListFragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentInvoiceListBinding
import retrofit2.Response
import java.util.HashMap

class InvoiceListFragment : Fragment()  {

    private var _binding: FragmentInvoiceListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var invoiceAdapter: InvoiceAdapter
    private lateinit var invoicelist: List<InvoiceViewModel>
    private var mAppLoader: AppLoader? = null
    var user_id = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        user_id =
            SharedPreferenceUtils.getInstance(requireContext()).getStringValue(ConstantUtils.ID, "")
                .toString()


        invoice()



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun invoice() {
        mAppLoader?.showDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)
        stringStringHashMap.put("booking_id", getArguments()?.getString("booking_id").toString())


        var registration = APIUtils.getServiceAPI()!!.booking_detail(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<InvoiceViewModel> {
            override fun onResponse(
                call: retrofit2.Call<InvoiceViewModel>,
                response: Response<InvoiceViewModel>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.showDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {

                            mAppLoader?.dismissDialog()

                            binding.rvInvoicelist.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvInvoicelist.layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            invoiceAdapter = InvoiceAdapter(response.body()!!.data,requireContext())

                            binding.rvInvoicelist.adapter = invoiceAdapter


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

            override fun onFailure(call: retrofit2.Call<InvoiceViewModel>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }



}
