package com.topcutlawn.Activity.GalleryActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Activity.ResidentialActivity.ResidentialAdapter
import com.topcutlawn.Fragments.HomeFragment.Adapters.GalleryBeforeAfterModel
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityGalleryBinding
import retrofit2.Response
import java.util.HashMap

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private var mAppLoader: AppLoader? = null
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var gallerylist: List<GalleryViewModel>
    var user_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAppLoader = AppLoader(this as Activity?)
        user_id =
            SharedPreferenceUtils.getInstance(this).getStringValue(ConstantUtils.ID, "")
                .toString()
        gallery_before_after()
        binding.ivNotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        binding.ivMenu.setOnClickListener {
            onBackPressed();
        }

     /*   binding.rvGallery.layoutManager = LinearLayoutManager(this)
        binding.rvGallery.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)*/

    }


    fun gallery_before_after() {
         mAppLoader?.showDialog()
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






                            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this@GalleryActivity, 2)
                            binding.rvGallery.setLayoutManager(layoutManager)
                            galleryAdapter =GalleryAdapter(response.body()!!.data, this@GalleryActivity)

                            binding.rvGallery.adapter = galleryAdapter

                           /* gallerylist=response.body()
                            galleryAdapter= GalleryAdapter(gallerylist, this@GalleryActivity)


                            val layoutManager = GridLayoutManager(this@GalleryActivity, 2)


                            binding.rvGallery.setLayoutManager(layoutManager)
                            binding.rvGallery.setAdapter(galleryAdapter);*/


                            /*val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this@GalleryActivity, 2)
                            binding.rvGallery.setLayoutManager(layoutManager)
                            galleryAdapter = GalleryAdapter(gallerylist, this@GalleryActivity)

                            binding.rvGallery.adapter = galleryAdapter*/


                        } else {

                            //  binding.rlLoader.visibility = View.GONE
                            mAppLoader?.dismissDialog()
                            Toast.makeText(
                                this@GalleryActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        //binding.rlLoader.visibility = View.GONE
                        mAppLoader?.dismissDialog()
                        // dialog.dismiss()

                        Toast.makeText(
                            this@GalleryActivity,
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
                Toast.makeText(this@GalleryActivity, t.toString(), Toast.LENGTH_SHORT).show()


            }

        })
    }

}