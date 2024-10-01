package com.example.jollycat.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jollycat.ui.main.cart.CartFragment
import com.example.jollycat.ui.main.home.HomeFragment
import com.example.jollycat.ui.main.profile.ProfileFragment
import com.example.jollycat.R
import com.example.jollycat.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())
        bottomNav = binding.bottomNav
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.action_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.action_cart -> {
                    loadFragment(CartFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}