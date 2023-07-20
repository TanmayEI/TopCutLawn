package com.topcutlawn.Activity.AddCardActivity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.topcutlawn.Activity.ViewContractActivity.ViewContractActivity
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.R
import com.topcutlawn.databinding.ActivityAddCardBinding
import com.topcutlawn.databinding.ActivityScheduleBinding


class AddCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvContinue.setOnClickListener {
            showDialog()
        }



        binding.ivMenu.setOnClickListener {
            onBackPressed();
        }
    }
    fun showDialog() {
        var dialog = Dialog(this@AddCardActivity)
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
            dialog.dismiss()
                val mainIntent = Intent(this, ViewContractActivity::class.java)
                startActivity(mainIntent)
        }
        tvpayletter.setOnClickListener {
            dialog.dismiss()
                val mainIntent = Intent(this, HomeActivity::class.java)
                startActivity(mainIntent)
        }

    }
}