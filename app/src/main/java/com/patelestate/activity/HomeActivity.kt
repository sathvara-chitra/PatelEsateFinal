package com.patelestate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.patelestate.R
import com.patelestate.base.BaseActivity
import com.patelestate.fragment.FavriteFragment
import com.patelestate.fragment.HomeFragment
import com.patelestate.fragment.ProfileFragment

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment()
                    openFragment(homeFragment)
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favourite -> {
                    val favFragment = FavriteFragment.newInstance("", "")
                    openFragment(favFragment)
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment.newInstance("", "")
                    openFragment(profileFragment)
//                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun init() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.itemIconTintList = null

        //Default
        val homeFragment = HomeFragment()
        openFragment(homeFragment)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
    }
}