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
import com.web.testtask.databinding.ItemPagerBinding

class ViewPagerAdapter(
    private val onDownload: (String, Drawable?, DataModel) -> Unit
) : BaseAdapter() {

    inner class ViewPagerHolder(private val binding: ItemPagerBinding) :
        BaseHolder(binding) {

        override fun bind(data: DataModel) {
            val dir = itemView.context.cacheDir.absolutePath
            binding.progressBar.isVisible = true
            binding.linear.isVisible = true
            binding.pagerGifView.setGif(data, data.images?.original?.url ?: "").listener(
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
                        onDownload(dir, resource, data)
                        binding.progressBar.isVisible = false
                        binding.linear.isVisible = true
                        return false
                    }
                }).into(binding.pagerGifView)

            binding.title.text = data.title
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding: ItemPagerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pager,
            parent, false
        )
        return ViewPagerHolder(binding)
    }
}