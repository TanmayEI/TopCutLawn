package com.topcutlawn.Activity.ViewCostActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Activity.PropertyManagement.PropertyModel
import com.topcutlawn.Activity.ResidentialActivity.ResidentialAdapter
import com.topcutlawn.Activity.ScheduleActivity.ScheduleActivity
import com.topcutlawn.R
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityGetCostBinding
import com.topcutlawn.databinding.ActivityViewCostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ViewCostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewCostBinding
    private var mAppLoader: AppLoader? = null
    var service_id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAppLoader = AppLoader(this as Activity?)
        binding.ivNotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        service_id=intent.getStringExtra("service_id").toString()
        binding.tvApproved.setOnClickListener {
            val mainIntent = Intent(this, ScheduleActivity::class.java)
            mainIntent.putExtra("service_id",service_id)
            startActivity(mainIntent)
        }
        binding.ivMenu.setOnClickListener {
            onBackPressed();
        }
        getestimate_cost()
    }

    fun getestimate_cost() {
        mAppLoader?.showDialog()

        var stringStringHashMap = java.util.HashMap<String, String>()

        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(this)
                .getStringValue(ConstantUtils.ID, "").toString(),
        )

        var userdetailsupdate = APIUtils.getServiceAPI()!!.view_estimate_cost(stringStringHashMap)
        userdetailsupdate.enqueue(object : Callback<ViewEstimateCostModel> {
            override fun onResponse(
                call: Call<ViewEstimateCostModel>,
                response: Response<ViewEstimateCostModel>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()
                            binding.selectService.layoutManager = LinearLayoutManager(this@ViewCostActivity)
                            binding.selectService.layoutManager =
                                LinearLayoutManager(this@ViewCostActivity, LinearLayoutManager.VERTICAL, false)
                            binding.selectService.adapter =
                                ViewCostAdapter(this@ViewCostActivity, response.body()!!.data)

                            binding.amount
                                .setText("\$"+response.body()!!.cost+".00")

                        } else {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                this@ViewCostActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        Toast.makeText(
                            this@ViewCostActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        this@ViewCostActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(
                call: Call<ViewEstimateCostModel>, t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@ViewCostActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }


}