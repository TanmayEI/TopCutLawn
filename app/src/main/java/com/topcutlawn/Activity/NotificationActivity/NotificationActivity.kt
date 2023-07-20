package com.topcutlawn.Activity.NotificationActivity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Fragments.HomeFragment.Adapters.RequestService
import com.topcutlawn.Fragments.HomeFragment.Adapters.RequestServiceAdapter
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityNotificationBinding
import retrofit2.Response
import java.util.HashMap

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private var mAppLoader: AppLoader? = null
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationList: List<NotificationViewModel>
    var user_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAppLoader = AppLoader(this as Activity?)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user_id =
            SharedPreferenceUtils.getInstance(this).getStringValue(ConstantUtils.ID, "")
                .toString()

        request_service()

        binding.ivMenu.setOnClickListener {
            onBackPressed()
        }

    }

    fun request_service() {
         mAppLoader?.showDialog()
        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)


        var registration = APIUtils.getServiceAPI()!!.notification(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<NotificationViewModel> {
            override fun onResponse(
                call: retrofit2.Call<NotificationViewModel>,
                response: Response<NotificationViewModel>
            ) {
                try {
                    if (response.code() == 200) {
                        mAppLoader?.dismissDialog()
                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {


                            binding.rvNotification.layoutManager = LinearLayoutManager(this@NotificationActivity)
                            binding.rvNotification.layoutManager =
                                LinearLayoutManager(this@NotificationActivity, LinearLayoutManager.VERTICAL, false)

                            notificationAdapter = NotificationAdapter(response.body()!!.data,
                                this@NotificationActivity,user_id)

                            binding.rvNotification.adapter = notificationAdapter

                        } else {

                            //  binding.rlLoader.visibility = View.GONE
                            mAppLoader?.dismissDialog()
                        }
                    } else {
                        //binding.rlLoader.visibility = View.GONE
                        mAppLoader?.dismissDialog()
                        // dialog.dismiss()

                    }
                } catch (e: Exception) {
                    //  binding.rlLoader.visibility = View.GONE
                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(call: retrofit2.Call<NotificationViewModel>, t: Throwable) {
                // binding.rlLoader.visibility = View.GONE
                mAppLoader?.dismissDialog()
               // Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }


}