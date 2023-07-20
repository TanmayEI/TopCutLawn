package com.topcutlawn.Activity.ResidentialActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityResidentialBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResidentialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResidentialBinding
    private var mAppLoader: AppLoader? = null
    private lateinit var residentialAdapter: ResidentialAdapter
    private lateinit var residentiallist: List<ResidentialViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //residentiallist()

        binding = ActivityResidentialBinding.inflate(layoutInflater)
        mAppLoader = AppLoader(this as Activity?)
        setContentView(binding.root)
        if (intent.getStringExtra("name").equals("Commercial Service")){
            serviceslist("2")
        }else{
            serviceslist("1")
        }


        binding.heading.setText(intent.getStringExtra("name"))
        binding.ivNotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

//        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
//        binding.rvRequestservices.setLayoutManager(layoutManager)

 /*       residentialAdapter = ResidentialAdapter(residentiallist, this)

        binding.rvRequestservices.adapter = residentialAdapter*/



        binding.ivMenu.setOnClickListener {
          onBackPressed();
        }

    }

    fun serviceslist(type: String) {
        mAppLoader?.showDialog()

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(this)
                .getStringValue(ConstantUtils.ID, "").toString(),
        )
        stringStringHashMap.put("type", type)

        var userdetailsupdate = APIUtils.getServiceAPI()!!.services(stringStringHashMap)
        userdetailsupdate.enqueue(object : Callback<ResidentialViewModel> {
            override fun onResponse(
                call: Call<ResidentialViewModel>,
                response: Response<ResidentialViewModel>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()

                            var list = ArrayList<ResidentialViewModel.Data>()
                            list.addAll(response.body()!!.data)
                            if (list != null) {
                                if (list.size > 0) {


                                    val adapter = ResidentialAdapter(list, this@ResidentialActivity)

                                    // setting grid layout manager to implement grid view.
                                    // in this method '2' represents number of columns to be displayed in grid view.

                                    // setting grid layout manager to implement grid view.
                                    // in this method '2' represents number of columns to be displayed in grid view.
                                    val layoutManager = GridLayoutManager(this@ResidentialActivity, 2)

                                    // at last set adapter to recycler view.

                                    // at last set adapter to recycler view.
                                    binding.rvRequestservices.setLayoutManager(layoutManager)
                                    binding.rvRequestservices.setAdapter(adapter);

                                    /*val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this@ResidentialActivity, 2)
                                    binding.rvRequestservices.setLayoutManager(layoutManager)
                                    binding.rvRequestservices.layoutManager = LinearLayoutManager(this@ResidentialActivity)

                                    binding.rvRequestservices.adapter =
                                        ResidentialAdapter(this@ResidentialActivity, list)*/
                                }
                            }



                        } else {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                this@ResidentialActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        Toast.makeText(
                            this@ResidentialActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        this@ResidentialActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(
                call: Call<ResidentialViewModel>, t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@ResidentialActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

 /*   fun residentiallist() {
        residentiallist = listOf(
            ResidentialViewModel(
                "Property Management",
            ),
            ResidentialViewModel(
                "Landscape Design",
            ),
            ResidentialViewModel(
                "Mulch and Rock Beds",
            ),
            ResidentialViewModel(
                "Debris and Leaf Removal",
            ),
            ResidentialViewModel(
                "Aeration",
            ),
            ResidentialViewModel(
                "Weed Control",
            ),
            ResidentialViewModel(
                "Residential Services",
            ),
        )
    }*/
}