package com.example.jollycat.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.jollycat.databinding.ActivityMainBinding
import com.example.jollycat.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var exerciseTimer: CountDownTimer ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exerciseTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                finish()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        exerciseTimer?.cancel()
        intent = null
    }
}