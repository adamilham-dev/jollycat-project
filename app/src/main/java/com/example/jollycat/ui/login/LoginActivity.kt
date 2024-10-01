package com.example.jollycat.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.jollycat.databinding.ActivityLoginBinding
import com.example.jollycat.ui.main.HomeActivity
import com.example.jollycat.ui.register.RegisterActivity
import com.example.jollycat.utils.Constants.showSnackbar
import com.example.jollycat.utils.Result
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var etUserName: EditText
    private lateinit var etPassword: EditText

    private val viewModel: LoginViewModel by inject<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        etUserName = binding.edUsername
        etPassword = binding.edPassword

        observeLoggedInUser()

        binding.btnLogin.setOnClickListener {
            if (validate()) {
                lifecycleScope.launch {
                    viewModel.login(
                        etUserName.text.toString(),
                        etPassword.text.toString()
                    )
                        .collect { result ->
                            when (result) {
                                is Result.Loading -> {

                                }

                                is Result.Success -> {
                                    if (result.data) {
                                        finishAffinity()
                                        val intent =
                                            Intent(this@LoginActivity, HomeActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        showSnackbar(
                                            this@LoginActivity,
                                            binding.root,
                                            "Unauthenticated"
                                        )
                                    }
                                }

                                is Result.Error -> {
                                    showSnackbar(
                                        this@LoginActivity, binding.root, "Unauthenticated"
                                    )
                                }
                            }
                        }
                }
            }
        }

        binding.tvSignInRedirect.setOnClickListener {
            finish()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validate(): Boolean {
        if (binding.edUsername.text.toString().isEmpty() || binding.edPassword.text.toString()
                .isEmpty()
        ) {
            showSnackbar(
                this@LoginActivity, binding.root, "Please fill out empty edit text"
            )
        } else {
            return true
        }
        return false
    }

    private fun observeLoggedInUser() {
        lifecycleScope.launch {
            viewModel.getSession().collect {
                if (it != -1) {
                    finishAffinity()
                    val intent =
                        Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}