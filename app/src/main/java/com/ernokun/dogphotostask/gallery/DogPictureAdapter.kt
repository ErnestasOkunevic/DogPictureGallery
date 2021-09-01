package com.ernokun.dogphotostask.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ernokun.dogphotostask.R
import com.ernokun.dogphotostask.databinding.ItemDogPictureBinding
import com.ernokun.dogphotostask.gallery.DogPictureAdapter.DogPictureViewHolder

class DogPictureAdapter(private val onClickItem: (String) -> Unit) :
    ListAdapter<String, DogPictureViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogPictureViewHolder {
        val binding = ItemDogPictureBinding.inflate(LayoutInflater.from(parent.context))
        return DogPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogPictureViewHolder, position: Int) {
        val url: String = getItem(position)
        holder.bind(url, onClickItem)
    }

    class DogPictureViewHolder(
        private val binding: ItemDogPictureBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String, onClickItem: (String) -> Unit) {
            binding.apply {
                val uri = url.toUri().buildUpon().scheme("https").build()

                itemDogPictureImage.apply {
                    load(uri) { error(R.drawable.ic_baseline_error_24) }
                    setOnClickListener { onClickItem(url) }
                }
            }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}