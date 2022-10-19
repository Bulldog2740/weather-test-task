package com.web.testtask.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.web.testtask.R
import com.web.testtask.core.basefragment.BaseFragment
import com.web.testtask.databinding.FragmentMainBinding
import com.web.testtask.presentation.adapter.CitiesPagingDataAdapter
import com.web.testtask.presentation.viewmodel.MainViewModel
import com.web.testtask.core.listener.CustomQueryTextListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val adapter: CitiesPagingDataAdapter by lazy(this::provideAdapter)
    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(CustomQueryTextListener { newText ->
            viewModel.getCities(newText)
        })
        initObserver()
        adapter.addLoadStateListener {
            if ((it.refresh is LoadState.NotLoading) && adapter.itemCount != 0)
                binding.progressBar.visibility = View.GONE
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.getAllCities.collectLatest {
                adapter.submitData(it)
            }
        }
        viewModel.ld.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(PagingData.empty())
                binding.recyclerView.scrollToPosition(0)
                adapter.submitData(it)
            }
        }
    }

    private fun provideAdapter() =
        CitiesPagingDataAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment(it)
            )
        }
}