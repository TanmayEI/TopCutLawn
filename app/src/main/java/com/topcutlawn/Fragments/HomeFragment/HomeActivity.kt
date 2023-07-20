package com.topcutlawn.Fragments.HomeFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.topcutlawn.Activity.GalleryActivity.GalleryActivity
import com.topcutlawn.Activity.NotificationActivity.NotificationActivity
import com.topcutlawn.Fragments.ChatFragment
import com.topcutlawn.Fragments.MyBookingFragment.MyBookingFragment
import com.topcutlawn.Fragments.ProfileFragment.ProfileFragment
import com.topcutlawn.MainActivity
import com.topcutlawn.R
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    lateinit var bottomNav: BottomNavigationView
    lateinit var navview: NavigationView
    lateinit var ivnotification: ImageView
    lateinit var ivMenu: ImageView
    lateinit var ivClose: ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var tvtitle: TextView
    lateinit var llprofilee: LinearLayout
    lateinit var llbooking: LinearLayout
    lateinit var llgallery: LinearLayout
    lateinit var llsupport: LinearLayout
    lateinit var lllogout: LinearLayout
    lateinit var profile:ImageView
    lateinit var name:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {

            // Adds our fragment
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<HomeFragment>(R.id.framelayout_home)

            }
        }

        fun loadFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout_home, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivnotification = findViewById(R.id.ivnotification)
        ivMenu = findViewById(R.id.ivMenu)
        ivClose = findViewById(R.id.iv_close)
        drawerLayout = findViewById(R.id.drawerLayout)
        tvtitle = findViewById(R.id.tv_title)
        llprofilee = findViewById(R.id.llprofilee)
        llbooking = findViewById(R.id.llbooking)
        llgallery = findViewById(R.id.llgallery)
        llsupport = findViewById(R.id.llsupport)
        lllogout = findViewById(R.id.lllogout)

        profile=findViewById(R.id.profile)
        name=findViewById(R.id.name)

        tvtitle.setText("Home")

        try {
            var status = "false"
            status = SharedPreferenceUtils.getInstance(this)?.getStringValue(
                ConstantUtils.IS_LOGIN, ""
            ).toString()
            if (status.equals("true")) {
                name.setText(SharedPreferenceUtils.getInstance(this)?.getStringValue(
                    ConstantUtils.USERNAME, ""
                ).toString())
                val url = SharedPreferenceUtils.getInstance(this)?.getStringValue(
                    ConstantUtils.IMAGE, ""
                ).toString().replace("http:", "https:")
                Picasso.with(this).load(url).into(profile)
            }else{

            }
        }catch (e:Exception){

        }


        ivnotification.setOnClickListener {
            intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        llprofilee.setOnClickListener {
            closeDrower()
            loadFragment(ProfileFragment())
            tvtitle.setText("Profile")
        }
        llbooking.setOnClickListener {
            closeDrower()
            loadFragment(MyBookingFragment())
            tvtitle.setText("My Booking")
        }
        llgallery.setOnClickListener {
            closeDrower()
            intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
        llsupport.setOnClickListener {
            closeDrower()
            loadFragment(ChatFragment())
            tvtitle.setText("Message")
        }
        lllogout.setOnClickListener {
            closeDrower()
            val preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ivClose.setOnClickListener {
            closeDrower()
        }



        //setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main_home)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        /*    binding.fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }*/

                bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.llHomee -> {
                    loadFragment(HomeFragment())
                    tvtitle.setText("Home")
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.llmybooking -> {
                    loadFragment(MyBookingFragment())
                    tvtitle.setText("My Booking")
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.llchat -> {
                    loadFragment(ChatFragment())
                    tvtitle.setText("Message")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.llprofile -> {
                    loadFragment(ProfileFragment())
                    tvtitle.setText("Profile")
                    return@setOnNavigationItemSelectedListener true

                }
            }
            false
        }

    }



    fun closeDrower() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_home)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun update_user_details(){
        var status = "false"
        status = SharedPreferenceUtils.getInstance(this)?.getStringValue(
            ConstantUtils.IS_LOGIN, ""
        ).toString()
        if (status.equals("true")) {
            name.setText(SharedPreferenceUtils.getInstance(this)?.getStringValue(
                ConstantUtils.USERNAME, ""
            ).toString())
            val url = SharedPreferenceUtils.getInstance(this)?.getStringValue(
                ConstantUtils.IMAGE, ""
            ).toString().replace("http:", "https:")
            Picasso.with(this).load(url).into(profile)
        }else{

        }
    }
}










  /*  : AppCompatActivity(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            // Adds our fragment
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<HomeFragment>(R.id.framelayout_home)

            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout_home, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}*/