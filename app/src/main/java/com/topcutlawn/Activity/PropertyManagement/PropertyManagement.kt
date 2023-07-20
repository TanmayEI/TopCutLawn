package com.topcutlawn.Activity.PropertyManagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.GetCostActivity.GetCostActivity
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Activity.ViewCostActivity.ViewCostActivity
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityPropertyManagementBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PropertyManagement : AppCompatActivity() {
    private lateinit var binding: ActivityPropertyManagementBinding
    private var mAppLoader: AppLoader? = null
    var service_id:String=""

    val service_list: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAppLoader = AppLoader(this as Activity?)
        binding = ActivityPropertyManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        servicesdetails(intent.getStringExtra("service_id").toString())
        service_id=intent.getStringExtra("service_id").toString()

        binding.heading.setText(intent.getStringExtra("name"))

        binding.rlShow.setOnClickListener {
            binding.tvData.visibility= View.VISIBLE
            binding.tvDownarro.visibility=View.GONE
            binding.tvUparro.visibility=View.VISIBLE

        }
        binding.tvUparro.setOnClickListener {
            binding.tvData.visibility=View.GONE
            binding.tvDownarro.visibility=View.VISIBLE
            binding.tvUparro.visibility=View.GONE
        }


        binding.rlShoww.setOnClickListener {
            binding.tvDataa.visibility= View.VISIBLE
            binding.tvDownarroo.visibility=View.GONE
            binding.tvUparroo.visibility=View.VISIBLE

        }
        binding.tvUparroo.setOnClickListener {
            binding.tvDataa.visibility=View.GONE
            binding.tvDownarroo.visibility=View.VISIBLE
            binding.tvUparroo.visibility=View.GONE
        }

        binding.rlShowww.setOnClickListener {
            binding.tvDataaa.visibility= View.VISIBLE
            binding.tvDownarrooo.visibility=View.GONE
            binding.tvUparrooo.visibility=View.VISIBLE

        }
        binding.tvUparrooo.setOnClickListener {
            binding.tvDataaa.visibility=View.GONE
            binding.tvDownarrooo.visibility=View.VISIBLE
            binding.tvUparrooo.visibility=View.GONE
        }



        binding.ivnotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.llcostestimate.setOnClickListener {
            /*val mainIntent = Intent(this, GetCostActivity::class.java)
            mainIntent.putParcelableArrayListExtra("servicelist",service_list!!)
            startActivity(mainIntent)*/


            val intent = Intent(this, GetCostActivity::class.java)
            intent.putExtra("service_list", service_list)
            intent.putExtra("service_id",service_id)
            startActivity(intent)


          /*  val birds: List<PropertyModel.Data.Services>?=null
            val intent = Intent(this, GetCostActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList("Birds", numbers)
            intent.putExtras(bundle)
            startActivity(intent)*/

        }
        binding.call.setOnClickListener {
            val mainIntent = Intent(this, ViewCostActivity::class.java)
            mainIntent.putExtra("service_id",service_id)
            startActivity(mainIntent)
            /*val mainIntent = Intent(this, ViewCostActivity::class.java)
            mainIntent.putExtra("service_id",intent.getStringExtra("service_id").toString())
            startActivity(mainIntent)*/
        }
        binding.ivMenu.setOnClickListener {
            onBackPressed();
        }
    }

    fun servicesdetails(service_id: String) {
        mAppLoader?.showDialog()

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(this)
                .getStringValue(ConstantUtils.ID, "").toString(),
        )
        stringStringHashMap.put("service_id", service_id)

        var userdetailsupdate = APIUtils.getServiceAPI()!!.service_detail(stringStringHashMap)
        userdetailsupdate.enqueue(object : Callback<PropertyModel> {
            override fun onResponse(
                call: Call<PropertyModel>,
                response: Response<PropertyModel>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()

                            val url = response.body()!!.data[0].image.replace("http:", "https:")
                            Picasso.with(this@PropertyManagement).load(url).into(binding.propertyImage)


                            binding.desc.setText(Html.fromHtml(response.body()!!.data[0].description))
                            binding.tvData.setText(Html.fromHtml(response.body()!!.data[0].qualification))
                            binding.tvDataa.setText(Html.fromHtml(response.body()!!.data[0].capabilities))
                            binding.tvDataaa.setText(Html.fromHtml(response.body()!!.data[0].commitment))

                            var size=response.body()!!.services_list.size

                            for (i in 0 until size){
                                service_list.add(response.body()!!.services_list[i].services)
                            }


                        } else {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                this@PropertyManagement,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        Toast.makeText(
                            this@PropertyManagement,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        this@PropertyManagement,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(
                call: Call<PropertyModel>, t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@PropertyManagement, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

}