package com.topcutlawn.Activity.NotificationActivity

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.API.APIUtils
import com.topcutlawn.R
import com.topcutlawn.databinding.NotificationListBinding
import retrofit2.Response
import java.util.HashMap

class NotificationAdapter (
    var notificationList: List<NotificationViewModel.Data>,
    val mContext: Context,
    val user_id:String

    ) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: NotificationListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            NotificationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(notificationList[position]) {

                if (notificationList[position].status.equals("Unread")){
                    binding.cardView.setCardBackgroundColor(Color.parseColor("#559C43"))
                    binding.tvMsg.setTextColor(Color.WHITE)
                    binding.tvName.setTextColor(Color.WHITE)
                    binding.tvTime.setTextColor(Color.WHITE)
                    binding.tick.setImageResource(R.drawable.check_icon)
                }else{
                    binding.cardView.setCardBackgroundColor(Color.parseColor("#E7FFE1"))
                    binding.tvMsg.setTextColor(Color.BLACK)
                    binding.tvName.setTextColor(Color.BLACK)
                    binding.tvTime.setTextColor(Color.BLACK)
                    binding.tick.setImageResource(R.drawable.rounded_green)
                }

                binding.tvName.text = notificationList[position].title
                binding.tvMsg.text = notificationList[position].message
                binding.tvTime.text = notificationList[position].time

                binding.cardView.setOnClickListener {
                    if (notificationList[position].status.equals("Unread")){
                        change_status(notificationList[position].id)
                        binding.cardView.setCardBackgroundColor(Color.parseColor("#E7FFE1"))
                        binding.tvMsg.setTextColor(Color.BLACK)
                        binding.tvName.setTextColor(Color.BLACK)
                        binding.tvTime.setTextColor(Color.BLACK)
                        binding.tick.setImageResource(R.drawable.rounded_green)
                    }else{

                    }

                }
            }

        }

    }

    fun change_status(notification_id:String) {

        //binding.rlLoader.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)
        stringStringHashMap.put("notification_id", notification_id)
        stringStringHashMap.put("status", "1")


        var registration = APIUtils.getServiceAPI()!!.change_status_of_notification(stringStringHashMap)
        registration.enqueue(object : retrofit2.Callback<NotificationChangeStaus> {
            override fun onResponse(
                call: retrofit2.Call<NotificationChangeStaus>,
                response: Response<NotificationChangeStaus>
            ) {
                try {
                    if (response.code() == 200) {

                        // binding.rlLoader.visibility = View.VISIBLE

                        if (response.body()!!.status == "1") {




                        } else {


                        }
                    } else {


                    }
                } catch (e: Exception) {


                }

            }

            override fun onFailure(call: retrofit2.Call<NotificationChangeStaus>, t: Throwable) {


            }

        })
    }


    override fun getItemCount(): Int {
        return notificationList.size
    }
}