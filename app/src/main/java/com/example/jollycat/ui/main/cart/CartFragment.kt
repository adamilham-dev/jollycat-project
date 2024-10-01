package com.example.jollycat.ui.main.cart

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jollycat.R
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.databinding.FragmentCartBinding
import com.example.jollycat.ui.adapter.TransactionAdapter
import com.example.jollycat.utils.Constants
import com.example.jollycat.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class CartFragment : Fragment() {
    private val viewModel by inject<CartViewModel>()

    private lateinit var binding: FragmentCartBinding

    private val transactionAdapter = TransactionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        observeCart()
        setRecyclerView()
        setListeners()

        return binding.root
    }

    private fun observeCart() {
        lifecycleScope.launch {
            viewModel.apply {
                getCartListFromDatabase().collect { result ->
                    when (result) {
                        is Result.Loading -> {

                        }

                        is Result.Success -> {
                            emptyView(result.data.isEmpty())
                            transactionAdapter.setCartList(result.data)
                        }

                        is Result.Error -> {
                            Constants.showSnackbar(
                                requireActivity(), binding.root, result.error
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnCheckout.setOnClickListener {
                val catIDList = transactionAdapter.getTransactionList().map { it.catID }
                val cartIDList =
                    transactionAdapter.getTransactionList().map { it.getTransactionID() }

                lifecycleScope.launch {
                    viewModel.checkoutCart(
                        catIDList
                    ).collect { result ->
                        when (result) {
                            is Result.Loading -> {

                            }

                            is Result.Success -> {
                                sendSMS(cartIDList, result.data)
                                observeCart()
                            }

                            is Result.Error -> {
                                Constants.showSnackbar(
                                    requireActivity(), binding.root, result.error
                                )
                            }
                        }
                    }
                }
            }

            transactionAdapter.onUpdateClick = { transaction ->
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCanceledOnTouchOutside(false)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.alert_custom_layout)
                val etUpdateQuantity: EditText = dialog.findViewById(R.id.updateQuantity)
                val btnConfirmButton: Button = dialog.findViewById(R.id.btnCustomAlertConfirmButton)
                val btnCancelButton: Button = dialog.findViewById(R.id.btnCustomAlertCancelButton)
                etUpdateQuantity.setText(transaction.getCatQuantity().toString())

                btnConfirmButton.setOnClickListener {
                    if (etUpdateQuantity.text.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please input quantity of 1 and more",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (etUpdateQuantity.text.toString().toInt() <= 0) {
                        Toast.makeText(
                            context,
                            "Please input quantity of 1 and more",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val values = ContentValues()
                        values.put(
                            DatabaseContract.CartColumns.QUANTITY,
                            etUpdateQuantity.text.toString()
                        )

                        lifecycleScope.launch {
                            viewModel.updateCartItem(
                                values,
                                transaction.catID
                            ).collect { result ->
                                when (result) {
                                    is Result.Loading -> {

                                    }

                                    is Result.Success -> {
                                        observeCart()
                                        dialog.dismiss()
                                    }

                                    is Result.Error -> {
                                        Constants.showSnackbar(
                                            requireActivity(), binding.root, result.error
                                        )
                                        dialog.dismiss()
                                    }
                                }
                            }
                        }
                    }
                }
                btnCancelButton.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }

            transactionAdapter.onDeleteClick = { transaction ->
                lifecycleScope.launch {
                    viewModel.deleteCartItem(
                        transaction.catID
                    ).collect { result ->
                        when (result) {
                            is Result.Loading -> {

                            }

                            is Result.Success -> {
                                if (result.data > 0) {
                                    observeCart()
                                } else {
                                    Constants.showSnackbar(
                                        requireActivity(),
                                        binding.root,
                                        "Failed to delete Cart Item!"
                                    )
                                }
                            }

                            is Result.Error -> {
                                Constants.showSnackbar(
                                    requireActivity(), binding.root, result.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.HistoryRV.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun sendSMS(cartIDList: List<Double>, checkoutID: Double) {
        if (checkSMSPermission()) {
            try {
                val phoneNumber = runBlocking { viewModel.getPhoneNumber().first().toString() }
                val cartIDStrings = cartIDList.joinToString { "$it" }
                val message =
                    "Your Cart ID ($cartIDStrings) has been Checked Out! (#$checkoutID)"
                val smsManager: SmsManager =
                    requireActivity().getSystemService(SmsManager::class.java)

                smsManager.sendTextMessage(phoneNumber, null, message, null, null)

                Constants.showSnackbar(
                    requireActivity(),
                    binding.root,
                    "Your cart has been Checked Out!, Message has been sent to your Phone Number!"
                )
            } catch (e: Exception) {
                Constants.showSnackbar(
                    requireActivity(), binding.root, e.message.toString()
                )
                e.printStackTrace()
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.SEND_SMS),
                1
            )
        }
    }

    private fun checkSMSPermission() = REQUIRES_SMS_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            requireActivity(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun emptyView(isEmpty: Boolean) {
        binding.layoutEmpty.isVisible = isEmpty
        binding.HistoryRV.isVisible = !isEmpty
        binding.btnCheckout.isVisible = !isEmpty
    }

    companion object {
        private val REQUIRES_SMS_PERMISSION = arrayOf(Manifest.permission.SEND_SMS)
    }
}