package com.example.jollycat.ui.register

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.model.User
import com.example.jollycat.utils.Constants.showSnackbar
import com.example.jollycat.databinding.ActivityRegisterBinding
import com.example.jollycat.ui.login.LoginActivity
import com.example.jollycat.ui.login.LoginViewModel
import com.example.jollycat.ui.main.HomeActivity
import com.example.jollycat.utils.Constants
import com.example.jollycat.utils.Result
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText

    private val viewModel: RegisterViewModel by inject<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            etPassword = binding.etRegisterPassword
            etName = binding.etRegisterName
            etPhoneNumber = binding.etRegisterPhoneNumber

            if (validate()) {
                lifecycleScope.launch {
                    val values = ContentValues()
                    values.put(DatabaseContract.UserColumns.USERNAME, etName.text.toString())
                    values.put(
                        DatabaseContract.UserColumns.PHONE_NUMBER,
                        etPhoneNumber.text.toString()
                    )
                    values.put(
                        DatabaseContract.UserColumns.PASSWORD,
                        etPassword.text.toString()
                    )

                    viewModel.register(
                        values
                    ).collect { result ->
                        when (result) {
                            is Result.Loading -> {

                            }

                            is Result.Success -> {
                                showSnackbar(
                                    this@RegisterActivity,
                                    binding.root,
                                    "Success! Please Login using the registered email!"
                                )

                                finish()
                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }

                            is Result.Error -> {
                                showSnackbar(
                                    this@RegisterActivity, binding.root, result.error
                                )
                            }
                        }
                    }
                }
            }
        }

        binding.tvLogInRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    //Validation Function
    private fun validate(): Boolean {
        if (etPassword.text.isEmpty() || etName.text.isEmpty() || etPhoneNumber.text.isEmpty()) {
            showSnackbar(
                this@RegisterActivity, binding.root, "Please Fill out all required forms"
            )
        } else if (etName.length() < 8) {
            showSnackbar(
                this@RegisterActivity,
                binding.root,
                "Username must have a minimum of 8 characters"
            )
        } else if (etPassword.text.length < 8) {
            showSnackbar(
                this@RegisterActivity,
                binding.root,
                "Password must have a minimum of 8 characters"
            )
        } else if (!validatePassword(etPassword.text.toString())) {
            showSnackbar(
                this@RegisterActivity,
                binding.root,
                "Password must contain at least 1 letter, 1 number, and 1 symbol."
            )
        } else if (!isPhoneValid(etPhoneNumber.text.toString())) {
            showSnackbar(
                this@RegisterActivity, binding.root, "Please Enter a valid phone number"
            )
        } else if (validateUniqueUser(etName.text.toString())) {
            showSnackbar(
                this@RegisterActivity,
                binding.root,
                "Username has been taken, please try another username"
            )
        } else {
//            //SAVE DATE TO DATABASE
//            val user = User(
//                Constants.getNewUserID(),
//                etName.text.toString(),
//                etPassword.text.toString(),
//                etPhoneNumber.text.toString(),
//            )
//            Constants.setUsers(user)

            return true
        }
        return false
    }

    private fun validateUniqueUser(inputtedName: String): Boolean {
        val userList = Constants.getUsers()
        for (user in userList) {
            if (user.getUserName() == inputtedName) {
                return true
            }
        }
        return false
    }

    // Function to validate an phonenumber
    private fun isPhoneValid(phoneNumber: String): Boolean {
        if (phoneNumber.length !in 8..20) {
            return false
        }

        if (!phoneNumber.startsWith("0") && !phoneNumber.startsWith("+")) {
            return false
        }

        val restOfNumber = phoneNumber.substring(1)
        return restOfNumber.all { it.isDigit() }
    }

    fun validatePassword(password: String): Boolean {
        val letterRegex = Regex("[a-zA-Z]")
        val numberRegex = Regex("[0-9]")
        val symbolRegex = Regex("[^a-zA-Z0-9]")

        val containsLetter = letterRegex.containsMatchIn(password)
        val containsNumber = numberRegex.containsMatchIn(password)
        val containsSymbol = symbolRegex.containsMatchIn(password)

        return containsLetter && containsNumber && containsSymbol
    }
}