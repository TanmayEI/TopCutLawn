package com.topcutlawn.Activity.ViewContractActivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Activity.PropertyManagement.PropertyManagement
import com.topcutlawn.Activity.ViewCostActivity.ViewCostAdapter
import com.topcutlawn.Activity.ViewCostActivity.ViewEstimateCostModel
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.Fragments.ProfileFragment.PropertyPictureViewModel
import com.topcutlawn.R
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityViewContractBinding
import es.voghdev.pdfviewpager.library.PDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream


class ViewContractActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewContractBinding
    private var mAppLoader: AppLoader? = null
    var pdfpath=""
    private val RECORD_REQUEST_CODE = 101
    private lateinit var pdfUri: Uri
    var inputStream:InputStream?=null
    var date:String=""
    var pdfViewPager:PDFViewPager?=null
    var service_id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewContractBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAppLoader = AppLoader(this as Activity?)

        date=intent.getStringExtra("date").toString()
        binding.ivMenu.setOnClickListener {
            val mainIntent = Intent(this, PropertyManagement::class.java)
            startActivity(mainIntent)
        }
        service_id=intent.getStringExtra("service_id").toString()
        binding.call.setOnClickListener {
            userimageupdate()

        }
        binding.llcancel.setOnClickListener {
            showDialognow()
        }

        binding.uploadPdf.setOnClickListener {
            /*   if (clickCount == 1)
               {*/
            if (checkPermission() == true) {

                val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                pdfIntent.type = "application/pdf"
                pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(pdfIntent, 12)
            } else {
                setupPermissions()
            }

        }



    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // For loading Image
        if (resultCode != RESULT_CANCELED) {
        }

        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {

                pdfUri = data?.data!!
                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        // Setting the PDF to the TextView
                        myCursor = applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            binding.pdfName.text = pdfName

                            pdfUri = data?.data!!
                            inputStream = contentResolver.openInputStream(pdfUri)
                            pdfpath=contentResolver.openInputStream(pdfUri).toString()

                            binding.pdfView.fromUri(uri)
                                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                                .enableSwipe(true) // allows to block changing pages using swipe
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                // allows to draw something on the current page, usually visible in the middle of the screen

                                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                                .password(null)
                                .scrollHandle(null)
                                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                                // spacing between pages in dp. To define spacing color, set view background
                                .spacing(0)


                                .load()

                            pdfViewPager = PDFViewPager(this, pdfpath)
                            (pdfViewPager!!.adapter as PDFPagerAdapter?)
                          //  showPdfFromUri(pdfUri)
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            }
        }
    }

/*
    private fun showPdfFromUri(uri: Uri?) {
        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .load()
    }
*/

    fun setupPermissions(): Boolean {
        val permissions =
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
               this,
                Manifest.permission.CAMERA
            ))
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to record denied")
            makeRequest()
        }
        return true
    }
    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this as Activity, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), RECORD_REQUEST_CODE
        )
    }





    fun checkPermission(): Boolean {
        val cameraPermission =
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ))
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun userimageupdate() {
        mAppLoader?.showDialog()
        // rlLoader!!.visibility = View.VISIBLE

        val multiPartRepeatString = "imageUpdate"
        var document: MultipartBody.Part? = null

        val userId: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            SharedPreferenceUtils.getInstance(this).getStringValue(ConstantUtils.ID, "")
                .toString()
        )

        val service_id: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            service_id
        )
        val schedule_date: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            date
        )
        val txn_id: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            "123456"
        )
        val txn_amount: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            "1500"
        )
        val payment_type: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            "Card"
        )




        if (!pdfpath.isNullOrEmpty()) {

           /* val file = File(pdfpath)
            val signPicBody =
                RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), file)
            image1 = MultipartBody.Part.createFormData("image", file.name, signPicBody)*/

            val requestBody =
                RequestBody.create("application/pdf".toMediaTypeOrNull(), inputStream!!.readBytes())
            document = MultipartBody.Part.createFormData("document", "example.pdf", requestBody)

        }


        var call = APIUtils.getServiceAPI()!!.book_now(userId,service_id,schedule_date,txn_id,txn_amount,payment_type,document)

        call.enqueue(object : Callback<BooNowModel> {
            override fun onResponse(
                call: Call<BooNowModel>,
                response: Response<BooNowModel>
            ) {
                try {

                    if (response.body()!!.status == "1") {

                        mAppLoader?.dismissDialog()
                        //  rlLoader!!.visibility = View.GONE
                        payment(response.body()!!.booking_id)
                        //showDialog()
                        Toast.makeText(
                            this@ViewContractActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@ViewContractActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        mAppLoader?.dismissDialog()
                        // rlLoader!!.visibility = View.GONE

                    }

                } catch (e: Exception) {
                    Toast.makeText(this@ViewContractActivity, e.toString(), Toast.LENGTH_SHORT).show()
                    mAppLoader?.dismissDialog()
                    // rlLoader!!.visibility = View.GONE
                }


            }

            override fun onFailure(
                call: Call<BooNowModel>,
                t: Throwable
            ) {
                mAppLoader?.dismissDialog()
                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@ViewContractActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun payment(booking_id:String) {
        mAppLoader?.showDialog()

        var stringStringHashMap = java.util.HashMap<String, String>()

        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(this)
                .getStringValue(ConstantUtils.ID, "").toString(),
        )
        stringStringHashMap.put(
            "booking_id",
            booking_id,
        )
        stringStringHashMap.put(
            "currency",
            "usd",
        )
        stringStringHashMap.put(
            "amount",
            "1500",
        )



        var userdetailsupdate = APIUtils.getServiceAPI()!!.paypal(stringStringHashMap)
        userdetailsupdate.enqueue(object : Callback<PaypalModel> {
            override fun onResponse(
                call: Call<PaypalModel>,
                response: Response<PaypalModel>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()
                            val intent = Intent(this@ViewContractActivity,WebViewActivity::class.java)
                            intent.putExtra("webUrl",response.body()!!.payment_url)

                            startActivity(intent)

                        } else {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                this@ViewContractActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        Toast.makeText(
                            this@ViewContractActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        this@ViewContractActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    mAppLoader?.dismissDialog()

                }

            }

            override fun onFailure(
                call: Call<PaypalModel>, t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(this@ViewContractActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun showDialog() {
        var dialog = Dialog(this@ViewContractActivity)
        dialog.getWindow()!!
            .setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );

        lateinit var tvdone: TextView
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.payment_sucess_popup)
        tvdone = dialog.findViewById(R.id.tv_done)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        //dialog.window?.setLayout(9050, 1300)
        tvdone.setOnClickListener {
                val mainIntent = Intent(this, HomeActivity::class.java)
                startActivity(mainIntent)
        }
    }


    fun showDialognow() {
        var dialog = Dialog(this@ViewContractActivity)
        dialog.getWindow()!!
            .setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
        lateinit var tvpaynow: TextView
        lateinit var tvpayletter: TextView
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_card_options_popup)
        tvpaynow = dialog.findViewById(R.id.tv_paynow)
        tvpayletter = dialog.findViewById(R.id.tv_payletter)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        //dialog.window?.setLayout(9050, 1300)
        tvpaynow.setOnClickListener {
            val mainIntent = Intent(this, ViewContractActivity::class.java)
            startActivity(mainIntent)
        }
        tvpayletter.setOnClickListener {
                val mainIntent = Intent(this, HomeActivity::class.java)
                startActivity(mainIntent)
        }

    }
}