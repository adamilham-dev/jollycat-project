package com.example.jollycat.ui.catdetail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jollycat.R
import com.example.jollycat.data.local.DatabaseContract
import com.example.jollycat.data.model.Cat
import com.example.jollycat.data.model.Transaction
import com.example.jollycat.utils.Constants.formatToRupiah
import com.example.jollycat.utils.Constants.showSnackbar
import com.example.jollycat.databinding.ActivityCatDetailBinding
import com.example.jollycat.utils.Constants
import com.example.jollycat.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

import java.text.SimpleDateFormat
import java.util.*

class CatDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatDetailBinding
//    private val id = Constants.selectedID
//    private val cat = Constants.getCats()[id - 1]

    private val viewModel by inject<CatDetailViewModel>()

    private val cat by lazy {
        intent.getParcelableExtra(EXTRA_CAT, Cat::class.java)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCatDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        cat?.let { cat ->
            Glide.with(binding.root)
                .load(cat.CatImage)
                .placeholder(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.jollycat_logo_blue_small
                    )
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivDetailImage)

            binding.tvDetailName.text = cat.CatName
            binding.tvDetailDescription.text = cat.CatDescription
            binding.tvDetailPrice.text = formatToRupiah(cat.CatPrice)
        }

        binding.rlBackButton.setOnClickListener {
            finish()
        }

        binding.btnBuyProduct.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.alert_custom_layout)
            val etUpdateQuantity: EditText = dialog.findViewById(R.id.updateQuantity)
            val btnConfirmButton: Button = dialog.findViewById(R.id.btnCustomAlertConfirmButton)
            val btnCancelButton: Button = dialog.findViewById(R.id.btnCustomAlertCancelButton)

            btnConfirmButton.setOnClickListener {
                if (etUpdateQuantity.text.isEmpty()) {
                    showSnackbar(
                        this@CatDetailActivity,
                        binding.root,
                        "Please enter a valid quantity"
                    )
                } else if (etUpdateQuantity.text.toString()
                        .toInt() <= 0 || etUpdateQuantity.text.toString() == ""
                ) {
                    showSnackbar(
                        this@CatDetailActivity,
                        binding.root,
                        "Please enter a valid quantity"
                    )
                } else {
                    cat?.let { cat ->
                        lifecycleScope.launch {
                            viewModel.addCatToCart(
                                cat.CatID,
                                runBlocking { viewModel.getUserID().first().toString() },
                                etUpdateQuantity.text.toString().toInt()
                            ).collect { result ->
                                when (result) {
                                    is Result.Loading -> {}

                                    is Result.Success -> {
                                        if (result.data > 0) {
                                            finish()
                                            showSnackbar(
                                                this@CatDetailActivity,
                                                binding.root,
                                                "You purchased ${etUpdateQuantity.text} Cats! Check Cart for Details"
                                            )
                                            dialog.dismiss()
                                        } else {
                                            showSnackbar(
                                                this@CatDetailActivity,
                                                binding.root,
                                                "Failed to add ${etUpdateQuantity.text} cats to Cart!"
                                            )
                                            dialog.dismiss()
                                        }
                                    }

                                    is Result.Error -> {
                                        showSnackbar(
                                            this@CatDetailActivity,
                                            binding.root,
                                            result.error
                                        )
                                    }
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
    }

    companion object {
        const val EXTRA_CAT = "extra_cat"
    }
}