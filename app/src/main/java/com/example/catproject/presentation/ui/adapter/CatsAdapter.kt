package com.example.catproject.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.catproject.R
import com.example.catproject.databinding.ItemCatBinding
import com.example.catproject.domain.model.Cat

class CatsAdapter(
    private val clickListener: CatClickListener,
    private val context: Context
) :
    ListAdapter<Cat, CatsAdapter.CatViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)

        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)

        with(holder.binding) {
            Glide.with(context)
                .load(cat.url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(
                    RequestOptions()
                        .override(200, 200)
                        .fitCenter()
                )
                .into(image)

            buttonFavorite.setImageResource(
                if (cat.isFavorite) {
                    R.drawable.image_favorite
                } else {
                    R.drawable.image_no_favorite
                }
            )

            buttonFavorite.setOnClickListener { clickListener.onFavoriteClick(cat) }
            buttonDownload.setOnClickListener { clickListener.onDownloadClick(cat) }
        }
    }

    class CatViewHolder(val binding: ItemCatBinding) : ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }
    }

    interface CatClickListener {
        fun onFavoriteClick(cat: Cat)
        fun onDownloadClick(cat: Cat)
    }
}