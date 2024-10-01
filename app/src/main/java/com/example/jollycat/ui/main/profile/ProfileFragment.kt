package com.example.jollycat.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jollycat.databinding.FragmentProfileBinding
import com.example.jollycat.ui.aboutus.AboutUsActivity
import com.example.jollycat.ui.login.LoginActivity
import com.example.jollycat.utils.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {
    private val viewModel by inject<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProfileBinding =
            FragmentProfileBinding.inflate(inflater, container, false)

        binding.tvProfileName.text = runBlocking { viewModel.getUsername().first().toString() }
        binding.tvProfilePhoneNumber.text =
            runBlocking { viewModel.getPhoneNumber().first().toString() }

        binding.btnAboutUs.setOnClickListener {
            startActivity(Intent(requireActivity(), AboutUsActivity::class.java))
        }

        binding.btnLogOut.setOnClickListener {
            viewModel.logout()
            activity?.finishAffinity()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }

        return binding.root
    }
}