package com.web.testtask.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.web.testtask.R
import com.web.testtask.core.basefragment.BaseFragment
import com.web.testtask.databinding.FragmentTrendBinding
import com.web.testtask.presentation.adapter.GifListAdapter
import kotlinx.coroutines.launch

class ListGifsFragment : BaseFragment<FragmentTrendBinding>(R.layout.fragment_trend) {
    private val adapter: GifListAdapter by lazy {
        GifListAdapter {
            val action = ListGifsFragmentDirections.firstToSecond(it)
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.listGifs.collect {
                adapter.submitData(it)
                binding.swipeRefresh.isRefreshing = false
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.init()
        }

        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        }

        binding.recyclerView.adapter = adapter
        searchView()
        snackBar()
    }

    private fun searchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.textChange(newText ?: "")
                return true
            }
        })

        viewModel.isOnline.observe(viewLifecycleOwner) {
            binding.searchView.isVisible = it
        }
    }

    private fun snackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.offline),
            Snackbar.LENGTH_INDEFINITE
        )

        viewModel.isOnline.observe(viewLifecycleOwner) {
            if (it)
                snackBar.dismiss()
            else
                snackBar.show()
        }
    }
}