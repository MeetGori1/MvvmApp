package com.example.mvvmapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mvvmapp.R
import com.example.mvvmapp.databinding.ItemUnsplashImageBinding
import com.example.mvvmapp.model.ImageItem


class Paging3Adapter() : PagingDataAdapter<ImageItem, Paging3Adapter.PagingViewHolder>(ImagePagingDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Paging3Adapter.PagingViewHolder, position: Int) {
        val item: ImageItem? = getItem(position)
        item?.let {
            holder.setData(item)
        }
    }

    class PagingViewHolder(private val binding: ItemUnsplashImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: ImageItem) {
            binding.apply {
                Glide.with(itemView.context).load(item.urls.regular)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(androidx.databinding.library.baseAdapters.R.drawable.abc_ab_share_pack_mtrl_alpha)
                    .into(binding.imgImageView)
            }
        }
    }
}


object ImagePagingDiffUtil : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

}

