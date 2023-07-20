package com.topcutlawn.Activity.ViewContractActivity


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.gson.Gson
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.R
import com.topcutlawn.databinding.ActivityWebViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class WebViewActivity : AppCompatActivity() {
    private val TAG = "WebViewActivity"
    private lateinit var mViewBinding: ActivityWebViewBinding
    private var urlStr: String = ""
    var cpod: String = ""
    var PayerID: String = ""
    var token: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityWebViewBinding.inflate(LayoutInflater.from(this))
        setContentView(mViewBinding.root)
        openUrl()
    }

    private fun openUrl() {
        mViewBinding.progressBar.visibility = View.VISIBLE
        val webUrl = intent.getStringExtra("webUrl")
        /*val planJson = intent.getStringExtra(ConstantUtils.PLAN_JSON)
        if (!planJson!!.equals("")) {
            val gson = Gson()
            plan = gson.fromJson(planJson, Data::class.java)
        }*/
        Log.d(TAG, "openUrl: $webUrl")
        mViewBinding.webView.settings.javaScriptEnabled = true
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mViewBinding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance().setAcceptThirdPartyCookies(mViewBinding.webView, true);
        }
        mViewBinding.webView.loadUrl(webUrl.toString())
        mViewBinding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                mViewBinding.progressBar.visibility = View.GONE
                Log.d(TAG, "shouldOverrideUrlLoading: $url")
                if (url!!.contains("http://demo.equalinfotech.com/top_cut_lawn/api/successpayment")) {
                   /* val uri: Uri = Uri.parse(url)
                    cpod = uri.getQueryParameter("cpod").toString()
                    PayerID = uri.getQueryParameter("PayerID").toString()
                    token = uri.getQueryParameter("token").toString()*/
                    //openDialog(false)
                    showDialog()

                } else if (url.contains("http://demo.equalinfotech.com/top_cut_lawn/api/success_payment")) {

                   // Log.d(TAG, "shouldOverrideUrlLoading: $planJson")
                   /* val uri: Uri = Uri.parse(url)
                    cpod = uri.getQueryParameter("cpod").toString()
                    PayerID = uri.getQueryParameter("PayerID").toString()
                    token = uri.getQueryParameter("token").toString()*/
//                    SharedPreferenceUtils.getInstance(this@WebViewActivity)
//                        .setStringValue(ConstantUtils.SUBSCRIPTION_DATE)
                   // openDialog(false)
                    showDialog()
                } else if (url.contains("stripe_payment_success")) {
                  //  openDialog(true)
                    showDialog()
                }else if(url.contains("stripe_payment_failed")){
                   // paymentFailureDialog()
                    showDialog()
                } else {
                    mViewBinding.progressBar.visibility = View.VISIBLE
                    view.loadUrl(url)
                }
//                Log.d(TAG, "shouldOverrideUrlLoading: $url")
//                val loadWebUrl: String = view.url
//                Log.d(TAG,"loadWebUrl $loadWebUrl" )
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                mViewBinding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

        }

    }

    fun showDialog() {
        var dialog = Dialog(this)
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


    /* private fun openDialog(isStrip: Boolean) {
         val dialogBinding = PaymentSuccessDialogBinding.inflate(LayoutInflater.from(this))
         val dialog = Dialog(this)
         dialog.setContentView(dialogBinding.root)
         dialog.setCancelable(false)
         dialogBinding.tvSuccessMsg.text =
             getString(R.string.your_payment_has_been_done_successfully)
         dialogBinding.tvPaymentDoneBtn.setOnClickListener {
             if (isStrip) {
                 dialog.dismiss()
                 val intent = Intent(this, DashboardActivity::class.java)
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                 intent.putExtra(ConstantUtils.KEY_FROM,"orderplaced")
                 startActivity(intent)
                 finish()
             } else {
                 dialog.dismiss()
                // updatepaypal_status()
             }


         }
         dialog.show()


     }

     private fun paymentFailureDialog() {
         val dialogBinding = PaymentFailureDialogBinding.inflate(LayoutInflater.from(this))
         val dialog = Dialog(this)
         dialog.setContentView(dialogBinding.root)
         dialog.setCancelable(false)

         dialogBinding.tvPaymentDoneBtn.setOnClickListener {
             dialog.dismiss()
             val intent = Intent(this, DashboardActivity::class.java)
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
             intent.putExtra(ConstantUtils.KEY_FROM,intent.getStringExtra(ConstantUtils.KEY_FROM))
             startActivity(intent)
             finish()
         }
         dialog.show()


     }*/

/*
    private fun updatepaypal_status() {


        val call: Call<PaypalStatusModal> =
            APIUtils.getServiceAPI()!!.updatestatuspaypale(cpod, PayerID, token)

        call.enqueue(object : Callback<PaypalStatusModal> {
            override fun onResponse(
                call: Call<PaypalStatusModal>,
                response: Response<PaypalStatusModal>
            ) {

                try {
                    if (response.isSuccessful && response.body()?.success.equals("true", false)) {
                        val intent = Intent(applicationContext, DashboardActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.putExtra(ConstantUtils.KEY_FROM,"orderplaced")
                        startActivity(intent)
                        finish()
                    } else {
                        if (response.body() != null) {
                            Toast.makeText(
                                this@WebViewActivity,
                                response.body()?.msg!!,
                                Toast.LENGTH_SHORT
                            ).show()


                        } else if (response.errorBody() != null) {
                            try {
                                val str = response.errorBody()!!.string()
                                Log.d(
                                    TAG,
                                    "onError: $str"
                                )
                                Toast.makeText(this@WebViewActivity, str, Toast.LENGTH_SHORT).show()

                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(
                                this@WebViewActivity,
                                "Something went wrong!",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } catch (e: Exception) {

                }

            }

            override fun onFailure(call: Call<PaypalStatusModal>, t: Throwable) {
                Toast.makeText(this@WebViewActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
    }
*/


}