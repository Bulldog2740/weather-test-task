package com.web.testtask.core.baseadapter

import android.graphics.drawable.Drawable
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.web.testtask.AppDelegate.Companion.GIF
import com.web.testtask.data.model.DataModel
import java.io.File

abstract class BaseAdapter : PagingDataAdapter<DataModel, BaseAdapter.BaseHolder>(diff) {

    open class BaseHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(data: DataModel) {}

        protected fun ShapeableImageView.setGif(data: DataModel, url: String): RequestBuilder<Drawable> {
            val path = itemView.context.cacheDir.absolutePath
            val model = if (data.isDownloaded) File(path, data.id + GIF) else url

            return Glide.with(context).load(model)
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bind(item)
    }
}

object diff : DiffUtil.ItemCallback<DataModel>() {
    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem == newItem
    }
}