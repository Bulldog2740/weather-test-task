package com.web.testtask.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.web.testtask.R
import com.web.testtask.data.model.DataModel
import com.web.testtask.databinding.FragmentDetailsBinding
import com.web.testtask.presentation.adapter.ViewPagerAdapter
import com.web.testtask.core.basefragment.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {
    private val args: DetailsFragmentArgs by navArgs()
    private var currentItem: DataModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = savedInstanceState?.getInt(KEY_POSITION) ?: args.page

        val adapter = ViewPagerAdapter { dir, drawable, data ->
            currentItem = data
            viewModel.downloadGif(dir, drawable, data)
        }

        lifecycleScope.launch {
            viewModel.listGifs.collect(){
                adapter.submitData(lifecycle, it)
            }
        }
        binding.delete.setOnClickListener {
            currentItem?.let { item ->
                viewModel.deletenOne(
                    requireContext().cacheDir.absolutePath,
                    item
                )
            }
        }

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.viewPager.adapter = adapter
        viewModel.isOnline.observe(viewLifecycleOwner) {
            binding.delete.isVisible = !it
        }

        binding.viewPager.doOnPreDraw {
            binding.viewPager.setCurrentItem(position, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_POSITION, binding.viewPager.currentItem)
    }

    companion object {
        private const val KEY_POSITION = "position"
    }
}