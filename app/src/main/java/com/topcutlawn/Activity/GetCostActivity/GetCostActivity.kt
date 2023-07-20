package com.topcutlawn.Activity.GetCostActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Activity.ViewCostActivity.ServiceListAdapter
import com.topcutlawn.Activity.ViewCostActivity.ViewCostActivity
import com.topcutlawn.Activity.ViewCostActivity.ViewCostAdapter
import com.topcutlawn.Activity.ViewCostActivity.ViewEstimateCostModel
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityGetCostBinding
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetCostActivity : AppCompatActivity(),GetCostNavigator {

    private var mAppLoader: AppLoader? = null
    var services:String=""
    val jObjectType = JSONArray()
    val jArray = JSONArray()
    var service_id:String=""
    private lateinit var binding: ActivityGetCostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetCostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAppLoader = AppLoader(this as Activity?)
        binding.ivNotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        service_id=intent.getStringExtra("service_id").toString()
        binding.tvVerifynow.setOnClickListener {

            var data=jArray.toString()

          /*  val re = "[^A-Za-z0-9 ]".toRegex()
            data = re.replace(data, "")*/

            data = data.replace("[", "")

            data = data.replace("]", "")

            data = data.replace("\"", "")

           // Toast.makeText(this,data,Toast.LENGTH_SHORT).show()

            if (binding.tvMsg.text.toString().equals("") ){
                Toast.makeText(this,"Input some message",Toast.LENGTH_SHORT).show()
            }else if (data.equals("")){
                Toast.makeText(this,"Tick atleast one service",Toast.LENGTH_SHORT).show()


            }else{
                getCostActivity(data)
            }

          /*  val mainIntent = Intent(this, ViewCostActivity::class.java)
            startActivity(mainIntent)*/
        }
        binding.ivMenu.setOnClickListener {
            onBackPressed();
        }

     //   getestimate_cost()

        val service_list = intent.getSerializableExtra("service_list") as ArrayList<String>?
        binding.getSelectService.layoutManager = LinearLayoutManager(this@GetCostActivity)
        binding.getSelectService.layoutManager =
            LinearLayoutManager(this@GetCostActivity, LinearLayoutManager.VERTICAL, false)
        binding.getSelectService.adapter =
            ServiceListAdapter(this@GetCostActivity, service_list!!,this@GetCostActivity)

    }

    fun getCostActivity(service: String) {
        mAppLoader?.showDialog()

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(this)
                .getStringValue(ConstantUtils.ID, "").toString(),
        )
        stringStringHashMap.put("service_id", service_id)
        stringStringHashMap.put("services", service)
        stringStringHashMap.put("message", binding.tvMsg.text.toString())

        var userdetailsupdate = APIUtils.getServiceAPI()!!.get_estimate_cost(stringStringHashMap)
        userdetailsupdate.enqueue(object : Callback<GetEstimateModel> {
            override fun onResponse(
                call: Call<GetEstimateModel>,
                response: Response<GetEstimateModel>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()

                            val mainIntent = Intent(this@GetCostActivity, ViewCostActivity::class.java)
                            mainIntent.putExtra("service_id",service_id)
                            startActivity(mainIntent)

                            Toast.makeText(
                                this@GetCostActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()



                        } else {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                this@GetCostActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        Toast.makeText(
                            this@GetCostActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        this@GetCostActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(
                call: Call<GetEstimateModel>, t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@GetCostActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }



    override fun onClick(data: String,pos:Int,status:String) {

        if (status.equals("check")){


            jArray.put(data)

        }else{
            for (i in 0 until jArray.length()){
                if (jArray[i].equals(data)){
                    jArray.remove(i) // For remove JSONArrayElement
                    return
                }else{
                   // jArray.remove(pos) // For remove JSONArrayElement
                }
            }

        }




    }


}