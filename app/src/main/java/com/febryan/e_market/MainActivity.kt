package com.febryan.e_market

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.febryan.e_market.activity.WelcomeActivity
import com.febryan.e_market.databinding.ActivityMainBinding
import com.febryan.e_market.fragment.DashboardFragment
import com.febryan.e_market.fragment.HomeFragment
import com.febryan.e_market.fragment.NotificationsFragment
import com.febryan.e_market.helper.SharedPreference

class MainActivity : AppCompatActivity() {

    val fragmentHome: Fragment = HomeFragment()
    val fragmentCart: Fragment = DashboardFragment()
    val fragmentAccount: Fragment = NotificationsFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

//    private var statusLogin = false
    private lateinit var sPH: SharedPreference

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sPH = SharedPreference(this)
        setUpBottomNav()

    }

    private fun setUpBottomNav() {

        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentCart).hide(fragmentCart).commit()
        fm.beginTransaction().add(R.id.container, fragmentAccount).hide(fragmentAccount).commit()

//        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView = binding.navView
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.navigation_home -> {
                    callFragment(0, fragmentHome)
                }

                R.id.navigation_dashboard -> {
                    callFragment(1, fragmentCart)
                }

                R.id.navigation_notifications -> {

                    if (sPH.getStatusLogin()){
                        callFragment(2, fragmentAccount)
                    }else{
                        val i = Intent(this, WelcomeActivity::class.java)
                        startActivity(i)
//                        Toast.makeText(this,"Login Dulu !", Toast.LENGTH_SHORT).show()
                    }

                }
            }

          false
        }

    }

    private fun callFragment(index: Int, fragment: Fragment) {
        menuItem = menu.getItem(index)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}