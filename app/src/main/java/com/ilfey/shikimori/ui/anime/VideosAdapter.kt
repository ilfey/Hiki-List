package com.ilfey.shikimori.ui.anime

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilfey.shikimori.databinding.ItemVideoBinding
import com.ilfey.shikimori.di.network.models.Anime


class VideosAdapter(
    private var list: List<Anime.Video>
) : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: List<Anime.Video>) {
        list = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val binding: ItemVideoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Anime.Video) {
            binding.root.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(binding.root.context, browserIntent, null)
            }

            Glide
                .with(binding.video)
                .load(item.image_url)
                .into(binding.video)

            binding.title.text = item.name
        }
    }
}