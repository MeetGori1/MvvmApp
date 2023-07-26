package com.example.mvvmapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mvvmapp.R
import com.example.mvvmapp.databinding.ItemUnsplashImageBinding
import com.example.mvvmapp.model.ImageItem


class DiffUtilListAdapter() : ListAdapter<ImageItem, DiffUtilListAdapter.ViewHolder>(ImageDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.setData(user)
    }

    class ViewHolder(private val binding: ItemUnsplashImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: ImageItem) {
            binding.apply {
                Glide.with(itemView.context).load(item.urls.regular)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imgImageView)
            }
        }
        }
}


object ImageDiffUtil : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

}