package com.example.jollycat.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.jollycat.databinding.FragmentHomeBinding
import com.example.jollycat.ui.adapter.CatAdapter
import com.example.jollycat.utils.Constants
import com.example.jollycat.utils.Result
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by inject<HomeViewModel>()
    private val catAdapter = CatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeViewModel()

        val recyclerView = binding.ProductRV
        recyclerView.adapter = catAdapter
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.fetchCatListDatabase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            handleEmptyCatList()
                        } else {
                            showLoading(false)
                            catAdapter.setCatList(result.data)
                        }
                    }

                    is Result.Error -> {
                        handleEmptyCatList()
                    }
                }
            }
        }
    }

    private suspend fun handleEmptyCatList() {
        viewModel.fetchCatListFromInternet().collect { resultInternet ->
            when (resultInternet) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    catAdapter.setCatList(resultInternet.data)
                }

                is Result.Error -> {
                    showLoading(false)
                    Constants.showSnackbar(
                        requireActivity(), binding.root, resultInternet.error
                    )
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }
}