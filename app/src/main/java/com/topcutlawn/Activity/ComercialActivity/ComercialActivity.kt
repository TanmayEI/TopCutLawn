package com.topcutlawn.Activity.ComercialActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Fragments.HomeFragment.Adapters.HomeAdapter
import com.topcutlawn.Fragments.HomeFragment.HomeViewModel
import com.topcutlawn.R
import com.topcutlawn.databinding.ActivityComercialBinding
import com.topcutlawn.databinding.ActivityResidentialBinding

class ComercialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComercialBinding

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homedetailslist: List<HomeViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homedetailslist()

        binding = ActivityComercialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivNotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        binding.rvRequestservices.setLayoutManager(layoutManager)
        homeAdapter = HomeAdapter(homedetailslist, this)


        binding.rvRequestservices.adapter = homeAdapter
    }

    fun homedetailslist() {
        homedetailslist = listOf(
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
            HomeViewModel(
                "Commercial Services",
            ),
        )
    }
}