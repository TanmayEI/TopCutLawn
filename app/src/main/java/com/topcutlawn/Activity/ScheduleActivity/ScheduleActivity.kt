package com.topcutlawn.Activity.ScheduleActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.AddCardActivity.AddCardActivity
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Activity.ViewContractActivity.ViewContractActivity
import com.topcutlawn.Activity.ViewCostActivity.ViewCostActivity
import com.topcutlawn.Activity.ViewCostActivity.ViewCostAdapter
import com.topcutlawn.Activity.ViewCostActivity.ViewEstimateCostModel
import com.topcutlawn.R
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityScheduleBinding
import com.topcutlawn.databinding.ActivityViewCostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleBinding

    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    private var mAppLoader: AppLoader? = null
    var service_id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAppLoader = AppLoader(this as Activity?)
        service_id=intent.getStringExtra("service_id").toString()
        binding.ivNotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.tvContinue.setOnClickListener {
            insertdate()

        }

        binding.ivMenu.setOnClickListener {
            onBackPressed();
        }




        dateTV = findViewById(R.id.idTVDate)
        calendarView = findViewById(R.id.calendarView)


        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        dateTV.setText(currentDate)
        // on below line we are adding set on
        // date change listener for calendar view.
        calendarView
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.


                    val Date = (year.toString() + "-"
                            + (month + 1) + "-" + dayOfMonth)

                    // set this date in TextView for Display
                    dateTV.setText(Date)
                })

    }

    fun insertdate() {
        mAppLoader?.showDialog()

        var stringStringHashMap = java.util.HashMap<String, String>()

        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(this)
                .getStringValue(ConstantUtils.ID, "").toString(),
        )
        stringStringHashMap.put("service_id", service_id)
        stringStringHashMap.put("schedule_date", dateTV.text.toString())

        var userdetailsupdate = APIUtils.getServiceAPI()!!.schedule_service_date(stringStringHashMap)
        userdetailsupdate.enqueue(object : Callback<ScheduleServiceModel> {
            override fun onResponse(
                call: Call<ScheduleServiceModel>,
                response: Response<ScheduleServiceModel>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()
                            val mainIntent = Intent(this@ScheduleActivity, ViewContractActivity::class.java)
                            mainIntent.putExtra("date", dateTV.text.toString())
                            mainIntent.putExtra("service_id",service_id)
                            startActivity(mainIntent)

                        } else {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                this@ScheduleActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        Toast.makeText(
                            this@ScheduleActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        this@ScheduleActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(
                call: Call<ScheduleServiceModel>, t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@ScheduleActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }


}
