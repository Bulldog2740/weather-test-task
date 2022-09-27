package com.web.testtask.core.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.web.testtask.presentation.viewmodel.GifViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<T : ViewDataBinding>(private val resId: Int) : Fragment() {

    protected lateinit var binding: T
    protected val viewModel: GifViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, resId, container, false
        )

        binding.lifecycleOwner = this

        return binding.root
    }

}