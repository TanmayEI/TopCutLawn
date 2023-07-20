package com.topcutlawn.SplashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.MainActivity
import com.topcutlawn.R
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils

class SplashActivity : AppCompatActivity() {
    val TIME_OUT:Long=3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadSplashScreen()
    }
    private fun loadSplashScreen(){
        Handler().postDelayed({
            var status = "false"
            status = SharedPreferenceUtils.getInstance(this)?.getStringValue(
                ConstantUtils.IS_LOGIN, ""
            ).toString()
            if (status.equals("true")) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)










      /*  Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        },TIME_OUT)*/
    }
}