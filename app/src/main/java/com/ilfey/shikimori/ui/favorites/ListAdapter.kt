package com.ilfey.shikimori.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.databinding.ItemFavoritesBinding
import com.ilfey.shikimori.di.network.models.Favorites

class ListAdapter(
    private val onClick: (Favorites.Entry) -> Unit = {},
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list: List<Favorites.Entry> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Favorites.Entry>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    inner class ViewHolder(
        private val binding: ItemFavoritesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Favorites.Entry) {

            Glide
                .with(binding.image.context)
                .load(BuildConfig.APP_URL + item.image)
                .into(binding.image)

            binding.title.text = item.russian
            binding.name.text = item.name

            binding.root.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }
}