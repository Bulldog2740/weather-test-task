package com.web.testtask.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.web.testtask.R
import com.web.testtask.core.baseadapter.BaseAdapter
import com.web.testtask.data.model.DataModel
import com.web.testtask.databinding.ItemGifBinding

class GifListAdapter(
    private val callbackId: (Int) -> Unit,
) : BaseAdapter() {
    inner class GifViewHolder(private val binding: ItemGifBinding) :
        BaseAdapter.BaseHolder(binding) {
        override fun bind(data: DataModel) {
            binding.progressBar.isVisible = true
            binding.gifView.isVisible = true
            binding.gifView.setGif(data, data.images?.previewGif?.url ?: "").listener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.isVisible = false
                        binding.gifView.isVisible = true
                        return false
                    }
                }).centerCrop().into(binding.gifView)
            binding.gifView.setOnClickListener {
                callbackId(absoluteAdapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding: ItemGifBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_gif,
            parent, false
        )
        return GifViewHolder(binding)
    }
}
